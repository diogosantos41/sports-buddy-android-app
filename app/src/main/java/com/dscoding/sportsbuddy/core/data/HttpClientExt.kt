package com.dscoding.sportsbuddy.core.data

import com.dscoding.sportsbuddy.BuildConfig
import com.dscoding.sportsbuddy.core.domain.DataError
import com.dscoding.sportsbuddy.core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        408 -> Result.Error(DataError.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.SERVER_ERROR)
        else -> Result.Error(DataError.UNKNOWN)
    }
}

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}