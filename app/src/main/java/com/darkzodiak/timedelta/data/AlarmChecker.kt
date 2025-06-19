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
// If no, reschedule them (WorkManager reschedules itself, so don't touch it)
class AlarmChecker(
    private val pendingAlarmDao: PendingAlarmDao,
    private val alarmEventDao: AlarmEventDao,
    private val alarmScheduler: AlarmScheduler
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun check(skipSystemAlarms: Boolean = false) {
        val time = System.currentTimeMillis()
        scope.launch {
            val alarms = pendingAlarmDao.getAllAlarms().first()
            alarms
                .filter { it.type != AlarmType.SCHEDULED_WORK }
                .filter { !isExpired(it, time) }
                .filterNot { skipSystemAlarms && it.isSystemAlarm }
                .forEach {
                    reschedule(it)
                }
        }
    }

    // If alarm is expired, returns true and sets an event. Else returns false
    private suspend fun isExpired(alarm: PendingAlarm, time: Long): Boolean {
        if(alarm.scheduled > time) return false

        alarmEventDao.addAlarm(
            AlarmEventEntity(null, alarm.type, alarm.scheduled, time, time - alarm.scheduled)
        )
        withContext(NonCancellable) {
            pendingAlarmDao.deleteAlarm(alarm)
        }

        return true
    }

    private fun reschedule(alarm: PendingAlarm) {
        alarmScheduler.schedule(alarm)
    }
}