package com.darkzodiak.timedelta.di

import androidx.work.WorkManager
import com.darkzodiak.timedelta.data.AlarmChecker
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.detectors.FirstLaunchPostBootDetector
import com.darkzodiak.timedelta.data.detectors.ForceStopDetector
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
    val alarmChecker: AlarmChecker
    val deltaRepository: DeltaRepository
    val workManager: WorkManager
    val delayedAlarmRunner: DelayedAlarmRunner
    val firstLaunchPostBootDetector: FirstLaunchPostBootDetector
    val forceStopDetector: ForceStopDetector
}