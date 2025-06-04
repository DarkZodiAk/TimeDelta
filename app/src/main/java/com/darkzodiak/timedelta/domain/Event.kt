package com.darkzodiak.timedelta.domain

sealed interface Event {
    val id: Long?
    val firedAt: Long

    data class AlarmEvent(
        override val id: Long?,
        val type: AlarmType,
        val scheduled: Long,
        override val firedAt: Long,
        val difference: Long
    ): Event

    data class AppEvent(
        override val id: Long?,
        override val firedAt: Long,
        val message: String
    ): Event
}