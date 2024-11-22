package com.dscoding.sportsbuddy.core.presentation

import java.time.Duration
import java.time.ZonedDateTime

fun calculateTimeRemaining(startTime: ZonedDateTime): Long {
    val currentTime = ZonedDateTime.now()
    return Duration.between(currentTime, startTime).seconds
}

fun formatSecondsToDisplayDate(seconds: Long): String {
    val hours = (seconds / 3600).toInt()
    val minutes = ((seconds % 3600) / 60).toInt()
    val remainingSeconds = (seconds % 60).toInt()
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}