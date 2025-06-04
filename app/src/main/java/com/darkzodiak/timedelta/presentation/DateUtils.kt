package com.darkzodiak.timedelta.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Long.formatTimestamp(): String {
    val sdf = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }
    return sdf.format(Date(this))
}

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat("MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }
    return sdf.format(Date(this))
}

fun Long.formatTimeNonZoned(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return sdf.format(Date(this))
}