@file:OptIn(ExperimentalLayoutApi::class)

package com.dscoding.sportsbuddy.sports.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbBlack
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbWhite
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SportsBuddyTheme
import com.dscoding.sportsbuddy.sports.domain.Sport

@Composable
fun SportItem(sport: Sport, modifier: Modifier = Modifier) {

    var areEventsVisible by remember {
        mutableStateOf(true)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SbWhite)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {}
            Text(
                text = sport.name,
                color = SbBlack,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { areEventsVisible = !areEventsVisible }) {
                Icon(
                    imageVector = if (areEventsVisible) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = if (areEventsVisible) {
                        stringResource(id = R.string.show_sport_events, sport.name)
                    } else {
                        stringResource(id = R.string.hide_sport_events, sport.name)
                    },
                    tint = SbBlack,
                    modifier = Modifier
                        .size(30.dp)
                        .minimumInteractiveComponentSize()
                )
            }
        }
        AnimatedVisibility(
            visible = areEventsVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                sport.events.forEach { event ->
                    EventItem(
                        event = event,
                        modifier = Modifier
                            .widthIn(max = 80.dp)
                            .heightIn(max = 80.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SportItemPreview() {
    SportsBuddyTheme {
        SportItem(
            Sport(
                id = "0",
                name = "Soccer",
                events = emptyList()
            )
        )
    }
}