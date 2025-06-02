package com.darkzodiak.timedelta.domain

import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import kotlinx.coroutines.flow.Flow

interface DeltaRepository {
    fun getAllPendingAlarms(): Flow<List<PendingAlarm>>
    suspend fun addAlarm(alarm: PendingAlarm)
    suspend fun deleteAlarm(alarm: PendingAlarm)
    suspend fun deleteAllPendingAlarms()

    fun getAllEvents(): Flow<List<Event>>
    suspend fun deleteAllEvents()
}