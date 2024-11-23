package com.dscoding.sportsbuddy.sports.presentation.model

import androidx.annotation.DrawableRes
import com.dscoding.sportsbuddy.core.presentation.UiText
import com.dscoding.sportsbuddy.core.presentation.calculateTimeRemaining
import com.dscoding.sportsbuddy.core.presentation.formatSecondsToDisplayDate
import com.dscoding.sportsbuddy.core.presentation.getDrawableIdForSport
import com.dscoding.sportsbuddy.sports.domain.Sport

data class SportUi(
    val id: String,
    val name: String,
    val showOnlyFavoriteEvents: Boolean,
    val isExpanded: Boolean,
    @DrawableRes val iconRes: Int,
    val events: List<EventUi>
) {

    val favoriteEvents: List<EventUi> get() = events.filter { event -> event.isFavorite }

    data class EventUi(
        val id: String,
        val remainingTime: DisplayableTime,
        val isFavorite: Boolean,
        val competitor1: String,
        val competitor2: String
    )
}

data class DisplayableTime(
    val remainingTime: Long,
    val remainingTimeToDisplay: UiText
)

fun Sport.Event.toEventUi(): SportUi.EventUi {
    return SportUi.EventUi(
        id = id,
        remainingTime = DisplayableTime(
            remainingTime = calculateTimeRemaining(startTime),
            remainingTimeToDisplay = UiText.DynamicString(
                formatSecondsToDisplayDate(
                    calculateTimeRemaining(startTime)
                )
            )
        ),
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




