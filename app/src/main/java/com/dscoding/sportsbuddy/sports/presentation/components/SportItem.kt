@file:OptIn(ExperimentalLayoutApi::class)

package com.dscoding.sportsbuddy.sports.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbBlack
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbDarkGrey
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbWhite
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SportsBuddyTheme
import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

@Composable
fun SportItem(
    sport: SportUi,
    onToggleExpandEvents: () -> Unit,
    onToggleFavoriteEvent: (eventId: String) -> Unit,
    onToggleShowOnlyFavorites: (showOnlyFavorites: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SbWhite)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = sport.iconRes),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = sport.name,
                color = SbBlack,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = sport.showOnlyFavoriteEvents,
                onCheckedChange = onToggleShowOnlyFavorites,
                thumbContent = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = SbWhite,
                        modifier = Modifier.padding(2.dp)
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = SbDarkGrey,
                    uncheckedThumbColor = SbDarkGrey.copy(alpha = 0.8f),
                    uncheckedTrackColor = SbDarkGrey.copy(alpha = 0.6f)
                )
            )
            IconButton(onClick = onToggleExpandEvents) {

                val rotationAngle by animateFloatAsState(
                    targetValue = if (sport.isExpanded) 180f else 0f,
                    label = ""
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (sport.isExpanded) {
                        stringResource(id = R.string.show_sport_events, sport.name)
                    } else {
                        stringResource(id = R.string.hide_sport_events, sport.name)
                    },
                    tint = SbBlack,
                    modifier = Modifier
                        .graphicsLayer { rotationZ = rotationAngle }
                        .size(40.dp)
                        .minimumInteractiveComponentSize()
                )
            }
        }
        AnimatedVisibility(
            visible = sport.isExpanded,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            if (sport.events.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
                ) {
                    Text(
                        text = stringResource(R.string.no_events, sport.name.lowercase()),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val maxItemWidth = maxWidth / 3
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        maxItemsInEachRow = 3
                    ) {
                        val eventsToDisplay =
                            if (sport.showOnlyFavoriteEvents) sport.favoriteEvents
                            else sport.events

                        eventsToDisplay.forEach { event ->
                            EventItem(
                                event = event,
                                onToggleFavoriteEvent = onToggleFavoriteEvent,
                                modifier = Modifier
                                    .width(maxItemWidth)
                                    .heightIn(max = 250.dp)
                                    .padding(vertical = 20.dp, horizontal = 10.dp)
                            )
                        }
                    }
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
            sport = SportUi(
                id = "0",
                name = "Soccer",
                showOnlyFavoriteEvents = true,
                isExpanded = true,
                iconRes = R.drawable.soccer,
                events = emptyList()
            ),
            onToggleExpandEvents = {},
            onToggleFavoriteEvent = {},
            onToggleShowOnlyFavorites = {}
        )
    }
}