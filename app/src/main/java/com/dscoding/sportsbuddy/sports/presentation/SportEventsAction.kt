package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

sealed interface SportEventsAction {
    data class OnToggleFavoriteEvent(val event: SportUi.EventUi) : SportEventsAction
    data class OnToggleSportVisibility(val sport: SportUi) : SportEventsAction
}