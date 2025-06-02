package com.darkzodiak.timedelta.data

import androidx.room.Database
import com.darkzodiak.timedelta.data.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.dao.AppEventDao
import com.darkzodiak.timedelta.data.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.entity.AlarmEvent
import com.darkzodiak.timedelta.data.entity.AppEvent
import com.darkzodiak.timedelta.data.entity.PendingAlarm

@Database(
    entities = [AlarmEvent::class, AppEvent::class, PendingAlarm::class],
    version = 1
)
abstract class DeltaDatabase {
    abstract val alarmEventDao: AlarmEventDao
    abstract val appEventDao: AppEventDao
    abstract val pendingAlarmDao: PendingAlarmDao

    companion object {
        const val DATABASE_NAME = "delta_db"
    }
}