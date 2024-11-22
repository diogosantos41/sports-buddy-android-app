package com.dscoding.sportsbuddy.sports.domain

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteEvents(): Flow<Set<String>>
    suspend fun markEventAsFavorite(id: String)
    suspend fun deleteEventFromFavorites(id: String)
}