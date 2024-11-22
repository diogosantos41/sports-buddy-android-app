package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

sealed interface SportEventsAction {
    data class OnToggleFavoriteEvent(val eventId: String) : SportEventsAction
    data class OnToggleExpandEvents(val sportId: String) : SportEventsAction
    data class OnChangeShowOnlyFavorites(val sportId: String, val showOnlyFavorites: Boolean) : SportEventsAction
}