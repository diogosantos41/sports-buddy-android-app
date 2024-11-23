package com.dscoding.sportsbuddy.sports.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.presentation.UiText
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SbYellow
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SportsBuddyTheme
import com.dscoding.sportsbuddy.sports.presentation.model.DisplayableTime
import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

@Composable
fun EventItem(
    event: SportUi.EventUi,
    onToggleFavoriteEvent: (eventId: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = event.remainingTime.remainingTimeToDisplay.asString(context),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxWidth()
                .padding(4.dp),
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        IconButton(onClick = { onToggleFavoriteEvent(event.id) }) {
            Icon(
                imageVector = if (event.isFavorite) {
                    Icons.Default.Star
                } else {
                    Icons.Default.StarBorder
                }, contentDescription = if (event.isFavorite) {
                    stringResource(R.string.mark_favorite)
                } else {
                    stringResource(R.string.remove_favorite)
                }, tint = if (event.isFavorite) {
                    SbYellow
                } else {
                    MaterialTheme.colorScheme.onBackground
                }, modifier = Modifier
                    .size(30.dp)
                    .minimumInteractiveComponentSize()
            )
        }
        Text(
            text = event.competitor1,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = stringResource(id = R.string.versus),
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = event.competitor2,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun EventItemPreview() {
    SportsBuddyTheme {
        EventItem(event = SportUi.EventUi(
            id = "0",
            remainingTime = DisplayableTime(0, UiText.DynamicString("10:30:45")),
            competitor1 = "Team 1",
            competitor2 = "Team 2",
            isFavorite = true,
        ), onToggleFavoriteEvent = {})
    }
}



