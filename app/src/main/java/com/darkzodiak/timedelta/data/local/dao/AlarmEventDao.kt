package com.darkzodiak.timedelta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmEventDao {
    @Insert
    suspend fun addAlarm(alarmEvent: AlarmEventEntity)

    @Query("DELETE FROM alarmevententity")
    suspend fun clearAllAlarms()

    @Query("SELECT * FROM alarmevententity")
    fun getAllAlarms(): Flow<List<AlarmEventEntity>>
}