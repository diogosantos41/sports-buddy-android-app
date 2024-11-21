package com.dscoding.sportsbuddy.sports.domain

import com.dscoding.sportsbuddy.core.domain.DataError
import com.dscoding.sportsbuddy.core.domain.Result

interface SportsDataSource {
    suspend fun getSports(): Result<List<Sport>, DataError>
}