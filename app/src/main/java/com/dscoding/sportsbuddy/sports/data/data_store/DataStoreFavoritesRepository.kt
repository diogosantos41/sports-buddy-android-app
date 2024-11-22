package com.dscoding.sportsbuddy.sports.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.dscoding.sportsbuddy.sports.data.data_store.DataStoreFavoritesRepository.PreferencesKeys.FAVORITE_EVENTS_KEY
import com.dscoding.sportsbuddy.sports.domain.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreFavoritesRepository(private val dataStore: DataStore<Preferences>) :
    FavoritesRepository {

    private object PreferencesKeys {
        val FAVORITE_EVENTS_KEY = stringSetPreferencesKey("favorite_events")
    }

    override fun getFavoriteEvents(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[FAVORITE_EVENTS_KEY] ?: emptySet()
        }
    }

    override suspend fun markEventAsFavorite(id: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITE_EVENTS_KEY] ?: emptySet()
            preferences[FAVORITE_EVENTS_KEY] = currentFavorites + id
        }
    }

    override suspend fun deleteEventFromFavorites(id: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITE_EVENTS_KEY] ?: emptySet()
            preferences[FAVORITE_EVENTS_KEY] = currentFavorites - id
        }
    }
}