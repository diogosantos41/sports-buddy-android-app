package com.dscoding.sportsbuddy.sports.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SportDto(
    @SerialName("i")
    val id: String,
    @SerialName("d")
    val name: String,
    @SerialName("e")
    val events: List<EventDto>
) {
    @Serializable
    data class EventDto(
        @SerialName("i")
        val id: String,
        @SerialName("si")
        val sportId: String,
        @SerialName("d")
        val name: String,
        @SerialName("tt")
        val startTime: Long
    )
}