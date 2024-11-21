package com.dscoding.sportsbuddy.sports.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SportsBuddyTheme

@Composable
fun SportEventsScreenRoot(viewModel: SportEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val onAction = viewModel::onAction

    SportEventsScreen(state = state, onAction = onAction)

}

@Composable
fun SportEventsScreen(state: SportEventsState, onAction: (SportEventsAction) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = state.sports, key = { it.id }) { sport ->
                Text(
                    text = sport.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun SportEventsScreenPreview() {
    SportsBuddyTheme {
        SportEventsScreen(
            state = SportEventsState(),
            onAction = {})
    }
}