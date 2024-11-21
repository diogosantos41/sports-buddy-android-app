package com.dscoding.sportsbuddy.sports.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SportsResponseDto(
    val sports: List<SportDto>
)
