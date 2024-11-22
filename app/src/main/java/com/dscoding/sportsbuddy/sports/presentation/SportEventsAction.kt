package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

sealed interface SportEventsAction {
    data class OnToggleFavoriteEvent(val eventId: String) : SportEventsAction
    data class OnToggleExpandEvents(val sportId: String) : SportEventsAction
    data class OnToggleShowOnlyFavorites(val showOnlyFavorites: Boolean) : SportEventsAction
}