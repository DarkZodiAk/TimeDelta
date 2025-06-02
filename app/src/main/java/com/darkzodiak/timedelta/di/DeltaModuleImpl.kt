package com.darkzodiak.timedelta.di

import android.content.Context
import androidx.room.Room
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.DeltaRepositoryImpl
import com.darkzodiak.timedelta.data.local.DeltaDatabase

class DeltaModuleImpl(
    private val context: Context
): DeltaModule {
    override val deltaDatabase by lazy {
        Room.databaseBuilder(
            context,
            DeltaDatabase::class.java,
            DeltaDatabase.DATABASE_NAME
        ).build()
    }

    override val alarmEventDao by lazy {
        deltaDatabase.alarmEventDao
    }

    override val appEventDao by lazy {
        deltaDatabase.appEventDao
    }

    override val pendingAlarmDao by lazy {
        deltaDatabase.pendingAlarmDao
    }

    override val alarmScheduler by lazy {
        AlarmScheduler(context, alarmEventDao, pendingAlarmDao)
    }

    override val deltaRepository by lazy {
        DeltaRepositoryImpl(
            alarmEventDao,
            appEventDao,
            pendingAlarmDao,
            alarmScheduler
        )
    }
}