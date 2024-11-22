package com.dscoding.sportsbuddy.sports.data.mappers

import com.dscoding.sportsbuddy.sports.data.model.SportDto
import com.dscoding.sportsbuddy.sports.domain.Sport
import java.time.Instant
import java.time.ZoneId


fun SportDto.EventDto.toEvent(): Sport.Event {
    return Sport.Event(
        id = id,
        sportId = sportId,
        competitors = competitors,
        startTime = Instant
            .ofEpochMilli(startTime * 1000)
            .atZone(ZoneId.of("UTC"))
    )
}

fun SportDto.toSport(): Sport {
    return Sport(
        id = id,
        name = name,
        events = events.map { it.toEvent() }
    )
}