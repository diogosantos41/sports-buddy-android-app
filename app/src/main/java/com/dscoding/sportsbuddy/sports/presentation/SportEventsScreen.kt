@file:OptIn(ExperimentalMaterial3Api::class)

package com.dscoding.sportsbuddy.sports.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.presentation.ErrorView
import com.dscoding.sportsbuddy.core.presentation.ui.theme.Poppins
import com.dscoding.sportsbuddy.core.presentation.ui.theme.SportsBuddyTheme
import com.dscoding.sportsbuddy.sports.presentation.components.SportItem

@Composable
fun SportEventsScreenRoot(viewModel: SportEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val onAction = viewModel::onAction

    SportEventsScreen(state = state, onAction = onAction)

}

@Composable
fun SportEventsScreen(state: SportEventsState, onAction: (SportEventsAction) -> Unit) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.primary.copy(0.1f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (state.errorMessage != null) {
                ErrorView(
                    errorMessage = state.errorMessage.asString(context),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = state.sports, key = { it.id }) { sport ->
                        SportItem(
                            sport = sport,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            onToggleExpandEvents = {
                                onAction(SportEventsAction.OnToggleExpandEvents(sport.id))
                            },
                            onToggleFavoriteEvent = {
                                onAction(SportEventsAction.OnToggleFavoriteEvent(it))
                            },
                            onToggleShowOnlyFavorites = {
                                onAction(SportEventsAction.OnChangeShowOnlyFavorites(sport.id, it))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SportEventsScreenPreview() {
    SportsBuddyTheme {
        SportEventsScreen(
            state = SportEventsState(),
            onAction = {})
    }
}