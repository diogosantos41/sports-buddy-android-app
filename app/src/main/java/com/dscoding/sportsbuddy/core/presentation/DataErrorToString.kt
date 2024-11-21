package com.dscoding.sportsbuddy.core.presentation

import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.domain.DataError

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        DataError.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        DataError.NO_INTERNET -> UiText.StringResource(R.string.error_internet_connection)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}