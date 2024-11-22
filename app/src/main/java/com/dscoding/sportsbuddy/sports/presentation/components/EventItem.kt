package com.dscoding.sportsbuddy.sports.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.sports.presentation.model.SportUi

@Composable
fun EventItem(event: SportUi.EventUi, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = event.competitor1, color = MaterialTheme.colorScheme.onBackground)
        Text(text = stringResource(id = R.string.versus), color = MaterialTheme.colorScheme.secondary)
        Text(text = event.competitor2, color = MaterialTheme.colorScheme.onBackground)
    }
}

