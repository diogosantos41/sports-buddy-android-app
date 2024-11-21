package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.core.presentation.UiText
import com.dscoding.sportsbuddy.sports.domain.Sport

data class SportEventsState(
    val sports: List<Sport> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
