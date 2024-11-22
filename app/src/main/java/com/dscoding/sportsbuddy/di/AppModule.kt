package com.dscoding.sportsbuddy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dscoding.sportsbuddy.core.data.HttpClientFactory
import com.dscoding.sportsbuddy.sports.data.data_source.KtorSportsDataSource
import com.dscoding.sportsbuddy.sports.data.data_store.DataStoreFavoritesRepository
import com.dscoding.sportsbuddy.sports.domain.FavoritesRepository
import com.dscoding.sportsbuddy.sports.domain.SportsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient = HttpClientFactory.create(CIO.create())

    @Provides
    @Singleton
    fun providesSportDataSource(httpClient: HttpClient): SportsDataSource {
        return KtorSportsDataSource(httpClient)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(dataStore: DataStore<Preferences>): FavoritesRepository {
        return DataStoreFavoritesRepository(dataStore)
    }
}