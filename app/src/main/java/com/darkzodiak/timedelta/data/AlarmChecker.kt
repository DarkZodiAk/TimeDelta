package com.darkzodiak.timedelta.data

import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import com.darkzodiak.timedelta.domain.AlarmType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Checks if alarms are outdated (called only first time after reboot)
// If yes, move them to alarm events with current time
// If no, reschedule them (WorkManager reschedules itself)
// TODO(Rename to AlarmRescheduler, separate functions for alarm manager and handler)
// TODO(Does WorkManager reschedule itself after force-stop?)
class AlarmChecker(
    private val pendingAlarmDao: PendingAlarmDao,
    private val alarmEventDao: AlarmEventDao,
    private val alarmScheduler: AlarmScheduler
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun check() {
        val time = System.currentTimeMillis()
        scope.launch {
            val alarms = pendingAlarmDao.getAllAlarms().first()
            alarms.forEach {
                rescheduleOrInvalidate(it, time)
            }
        }
    }


    private suspend fun rescheduleOrInvalidate(alarm: PendingAlarm, time: Long) {
        if(alarm.type == AlarmType.SCHEDULED_WORK) return
        if(alarm.scheduled > time) {
            alarmScheduler.schedule(alarm)
            return
        }
        alarmEventDao.addAlarm(
            AlarmEventEntity(null, alarm.type, alarm.scheduled, time, time - alarm.scheduled)
        )
        withContext(NonCancellable) {
            pendingAlarmDao.deleteAlarm(alarm)
        }
    }
}