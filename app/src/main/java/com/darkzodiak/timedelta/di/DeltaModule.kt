package com.darkzodiak.timedelta.di

import androidx.work.WorkManager
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.local.DeltaDatabase
import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.AppEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.worker.DelayedAlarmRunner
import com.darkzodiak.timedelta.domain.DeltaRepository

interface DeltaModule {
    val deltaDatabase: DeltaDatabase
    val alarmEventDao: AlarmEventDao
    val appEventDao: AppEventDao
    val pendingAlarmDao: PendingAlarmDao
    val alarmScheduler: AlarmScheduler
    val deltaRepository: DeltaRepository
    val workManager: WorkManager
    val delayedAlarmRunner: DelayedAlarmRunner
}