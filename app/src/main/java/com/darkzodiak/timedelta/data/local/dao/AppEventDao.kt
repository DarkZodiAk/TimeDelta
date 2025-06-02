package com.darkzodiak.timedelta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.darkzodiak.timedelta.data.local.entity.AppEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppEventDao {
    @Insert
    suspend fun addEvent(event: AppEventEntity)

    @Query("DELETE FROM appevententity")
    suspend fun clearAllEvents()

    @Query("SELECT * FROM appevententity")
    fun getAllEvents(): Flow<List<AppEventEntity>>
}