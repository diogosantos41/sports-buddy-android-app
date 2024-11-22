package com.dscoding.sportsbuddy.sports.presentation

import com.dscoding.sportsbuddy.core.presentation.UiText
import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

data class SportEventsState(
    val sports: List<SportUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
