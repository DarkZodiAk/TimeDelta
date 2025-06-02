package com.darkzodiak.timedelta.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.AppEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import com.darkzodiak.timedelta.data.local.entity.AppEventEntity
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm

@Database(
    entities = [AlarmEventEntity::class, AppEventEntity::class, PendingAlarm::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DeltaDatabase: RoomDatabase() {
    abstract val alarmEventDao: AlarmEventDao
    abstract val appEventDao: AppEventDao
    abstract val pendingAlarmDao: PendingAlarmDao

    companion object {
        const val DATABASE_NAME = "delta_db"
    }
}