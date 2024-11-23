package com.dscoding.sportsbuddy.sports.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.domain.onError
import com.dscoding.sportsbuddy.core.domain.onSuccess
import com.dscoding.sportsbuddy.core.presentation.UiText
import com.dscoding.sportsbuddy.core.presentation.toUiText
import com.dscoding.sportsbuddy.sports.domain.FavoritesRepository
import com.dscoding.sportsbuddy.sports.domain.SportsDataSource
import com.dscoding.sportsbuddy.sports.presentation.model.toSportUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportEventsViewModel @Inject constructor(
    private val dataSource: SportsDataSource,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SportEventsState())
    val state = _state
        .onStart { loadSportEvents() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SportEventsState()
        )

    fun onAction(action: SportEventsAction) {
        when (action) {
            is SportEventsAction.OnToggleFavoriteEvent -> {
                toggleFavoriteEvent(action.eventId)
            }

            is SportEventsAction.OnToggleExpandEvents -> {
                _state.update {
                    it.copy(
                        sports = it.sports.map { sport ->
                            if (sport.id == action.sportId) {
                                sport.copy(isExpanded = !sport.isExpanded)
                            } else {
                                sport
                            }
                        }
                    )
                }
            }

            is SportEventsAction.OnChangeShowOnlyFavorites -> {
                _state.update {
                    it.copy(
                        sports = it.sports.map { sport ->
                            if (sport.id == action.sportId) {
                                sport.copy(
                                    showOnlyFavoriteEvents = action.showOnlyFavorites,
                                )
                            } else {
                                sport
                            }
                        }
                    )
                }
            }
        }
    }

    private fun loadSportEvents() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            dataSource
                .getSports()
                .onSuccess { sports ->
                    if (sports.isEmpty()) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                sports = emptyList(),
                                errorMessage = UiText.StringResource(id = R.string.empty_list)
                            )
                        }
                        return@onSuccess
                    }
                    val sportsUi = sports.map { it.toSportUi() }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            sports = sportsUi,
                            errorMessage = null
                        )
                    }
                    observeFavorites()
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            sports = emptyList(),
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun toggleFavoriteEvent(eventId: String) {
        viewModelScope.launch {
            val isFavorite =
                _state.value.sports.flatMap { it.events }
                    .any { it.id == eventId && it.isFavorite }
            if (isFavorite) {
                favoritesRepository.deleteEventFromFavorites(eventId)
            } else {
                favoritesRepository.markEventAsFavorite(eventId)
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavoriteEvents().collect { favorites ->
                _state.update {
                    it.copy(
                        sports = it.sports.map { sport ->
                            sport.copy(
                                events = sport.events.map { event ->
                                    event.copy(isFavorite = favorites.contains(event.id))
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}