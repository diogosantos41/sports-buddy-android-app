package com.dscoding.sportsbuddy.core.presentation

import com.dscoding.sportsbuddy.R
import com.dscoding.sportsbuddy.core.domain.DataError

fun DataError.toUiText(): UiText {
    return when (this) {
        else -> UiText.StringResource(R.string.error_unknown)
    }
}