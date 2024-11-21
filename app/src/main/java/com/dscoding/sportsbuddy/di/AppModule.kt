package com.dscoding.sportsbuddy.di

import com.dscoding.sportsbuddy.core.data.HttpClientFactory
import com.dscoding.sportsbuddy.sports.data.data_source.KtorSportsDataSource
import com.dscoding.sportsbuddy.sports.domain.SportsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

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
}