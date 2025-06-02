package com.darkzodiak.timedelta.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingAlarmDao {
    @Insert
    suspend fun addAlarm(alarm: PendingAlarm): Long

    @Delete
    suspend fun deleteAlarm(alarm: PendingAlarm)

    @Query("DELETE FROM pendingalarm")
    suspend fun clearAllAlarms()

    @Query("SELECT * FROM pendingalarm")
    fun getAllAlarms(): Flow<List<PendingAlarm>>

    @Query("SELECT * FROM pendingalarm WHERE id=:id")
    suspend fun getAlarmById(id: Long): PendingAlarm?

    @Query("DELETE FROM pendingalarm WHERE id=:id")
    suspend fun deleteAlarmById(id: Long)
}