package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.sports.domain.Sport

sealed interface SportEventsAction {
    data class OnToggleFavoriteEvent(val event: Sport.Event) : SportEventsAction
    data class OnToggleSportVisibility(val sport: Sport) : SportEventsAction
}