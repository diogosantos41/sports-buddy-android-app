package com.dscoding.sportsbuddy.sports.domain

import java.time.ZonedDateTime


data class Sport(
    val id: String,
    val name: String,
    val events: List<Event>
) {
    data class Event(
        val id: String,
        val sportId: String,
        val competitors: String,
        val startTime: ZonedDateTime
    )
}
