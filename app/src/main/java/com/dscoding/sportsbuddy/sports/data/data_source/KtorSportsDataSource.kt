package com.dscoding.sportsbuddy.sports.data.data_source

import com.dscoding.sportsbuddy.core.data.constructUrl
import com.dscoding.sportsbuddy.core.data.safeCall
import com.dscoding.sportsbuddy.core.domain.DataError
import com.dscoding.sportsbuddy.core.domain.Result
import com.dscoding.sportsbuddy.core.domain.map
import com.dscoding.sportsbuddy.sports.data.mappers.toSport
import com.dscoding.sportsbuddy.sports.data.model.SportsResponseDto
import com.dscoding.sportsbuddy.sports.domain.Sport
import com.dscoding.sportsbuddy.sports.domain.SportsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class KtorSportsDataSource(private val httpClient: HttpClient) : SportsDataSource {
    override suspend fun getSports(): Result<List<Sport>, DataError> {
        return safeCall<SportsResponseDto> {
            httpClient.get(urlString = constructUrl("/sports"))
        }.map { response ->
            response.sports.map { it.toSport() }
        }
    }
}