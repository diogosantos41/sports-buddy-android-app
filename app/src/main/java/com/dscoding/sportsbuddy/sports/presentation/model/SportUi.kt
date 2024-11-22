package com.dscoding.sportsbuddy.sports.presentation.model

import androidx.annotation.DrawableRes
import com.dscoding.sportsbuddy.core.presentation.getDrawableIdForSport
import com.dscoding.sportsbuddy.sports.domain.Sport
import java.time.ZonedDateTime

data class SportUi(
    val id: String,
    val name: String,
    val showOnlyFavoriteEvents: Boolean,
    val isExpanded: Boolean,
    @DrawableRes val iconRes: Int,
    val events: List<EventUi>
) {
    data class EventUi(
        val id: String,
        val startTime: ZonedDateTime,
        val isFavorite: Boolean,
        val competitor1: String,
        val competitor2: String
    )
}

fun Sport.Event.toEventUi(): SportUi.EventUi {
    return SportUi.EventUi(
        id = id,
        startTime = startTime,
        isFavorite = false,
        competitor1 = competitors.splitCompetitors().first,
        competitor2 = competitors.splitCompetitors().second,
    )
}

fun Sport.toSportUi(): SportUi {
    return SportUi(
        id = id,
        name = name.lowercase().replaceFirstChar { it.uppercase() },
        showOnlyFavoriteEvents = false,
        isExpanded = true,
        iconRes = getDrawableIdForSport(id),
        events = events.map { it.toEventUi() }
    )
}

fun String.splitCompetitors(): Pair<String, String> {
    val competitors = split("-").map { it.trim() }
    return Pair(competitors.getOrNull(0) ?: "", competitors.getOrNull(1) ?: "")
}




