package com.darkzodiak.timedelta.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.darkzodiak.timedelta.data.entity.AppEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface AppEventDao {
    @Insert
    suspend fun addEvent(event: AppEvent)

    @Query("DELETE FROM appevent")
    suspend fun clearAllEvents()

    @Query("SELECT * FROM appevent")
    fun getAllEvents(): Flow<List<AppEvent>>
}