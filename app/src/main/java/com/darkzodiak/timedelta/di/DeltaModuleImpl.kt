package com.darkzodiak.timedelta.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.darkzodiak.timedelta.data.AlarmChecker
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.DeltaRepositoryImpl
import com.darkzodiak.timedelta.data.detectors.FirstLaunchPostBootDetector
import com.darkzodiak.timedelta.data.detectors.ForceStopDetector
import com.darkzodiak.timedelta.data.local.DeltaDatabase
import com.darkzodiak.timedelta.data.worker.DelayedAlarmRunner

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
        AlarmScheduler(context, pendingAlarmDao, delayedAlarmRunner)
    }

    override val alarmChecker by lazy {
        AlarmChecker(pendingAlarmDao, alarmEventDao, alarmScheduler)
    }

    override val deltaRepository by lazy {
        DeltaRepositoryImpl(
            alarmEventDao,
            appEventDao,
            pendingAlarmDao,
            alarmScheduler
        )
    }

    override val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override val delayedAlarmRunner by lazy {
        DelayedAlarmRunner(workManager)
    }

    override val firstLaunchPostBootDetector by lazy {
        FirstLaunchPostBootDetector(context)
    }

    override val forceStopDetector by lazy {
        ForceStopDetector(context)
    }
}