package com.darkzodiak.timedelta.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.darkzodiak.timedelta.data.entity.AlarmEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmEventDao {
    @Insert
    suspend fun addAlarm(alarmEvent: AlarmEvent)

    @Query("DELETE FROM alarmevent")
    suspend fun clearAllAlarms()

    @Query("SELECT * FROM alarmevent")
    fun getAllAlarms(): Flow<List<AlarmEvent>>
}