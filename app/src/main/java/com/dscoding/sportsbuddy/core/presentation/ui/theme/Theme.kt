package com.dscoding.sportsbuddy.core.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = SbBlue,
    onPrimary = SbWhite,
    background = SbDarkGrey,
    onBackground = SbWhite,
    secondary = SbOrange,
)

@Composable
fun SportsBuddyTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}