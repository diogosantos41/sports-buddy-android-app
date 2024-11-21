package com.dscoding.sportsbuddy.sports.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dscoding.sportsbuddy.sports.domain.Sport

@Composable
fun EventItem(event: Sport.Event, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = event.startTime.dayOfMonth.toString(), color = MaterialTheme.colorScheme.onBackground)
        Text(text = "vs.", color = MaterialTheme.colorScheme.secondary)
        Text(text = event.name, color = MaterialTheme.colorScheme.onBackground)
    }
}

