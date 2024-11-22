package com.dscoding.sportsbuddy.core.presentation

import com.dscoding.sportsbuddy.R

fun getDrawableIdForSport(sportId: String): Int {
    return when (sportId.uppercase()) {
        "FOOT" -> R.drawable.soccer
        "BASK" -> R.drawable.basketball
        "TENN" -> R.drawable.tennis
        "TABL" -> R.drawable.table_tennis
        "VOLL" -> R.drawable.volleyball
        "BCHV" -> R.drawable.beach_volleyball
        "ESPS" -> R.drawable.esports
        "BADM" -> R.drawable.badminton
        else -> R.drawable.sports
    }
}