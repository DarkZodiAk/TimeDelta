package com.darkzodiak.timedelta.domain

sealed interface Event {
    val id: Long?

    data class AlarmEvent(
        override val id: Long?,
        val type: AlarmType,
        val scheduled: Long,
        val firedAt: Long
    ): Event

    data class AppEvent(
        override val id: Long?,
        val time: Long,
        val message: String
    ): Event
}