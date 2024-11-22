package com.dscoding.sportsbuddy.sports.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.sportsbuddy.core.domain.onError
import com.dscoding.sportsbuddy.core.domain.onSuccess
import com.dscoding.sportsbuddy.core.presentation.toUiText
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
class SportEventsViewModel @Inject constructor(private val dataSource: SportsDataSource) :
    ViewModel() {

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
            is SportEventsAction.OnToggleFavoriteEvent -> {}
            is SportEventsAction.OnToggleExpandEvents -> {
                _state.update { currentState ->
                    currentState.copy(
                        sports = currentState.sports.map { sport ->
                            if (sport.id == action.sportId) {
                                sport.copy(isExpanded = !sport.isExpanded)
                            } else {
                                sport
                            }
                        }
                    )
                }
            }

            is SportEventsAction.OnToggleShowOnlyFavorites -> {}
        }
    }

    private fun loadSportEvents() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            dataSource
                .getSports()
                .onSuccess { sports ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            sports = sports.map { it.toSportUi() },
                            errorMessage = null
                        )
                    }
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
}