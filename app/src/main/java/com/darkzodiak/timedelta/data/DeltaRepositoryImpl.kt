package com.darkzodiak.timedelta.data

import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.AppEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import com.darkzodiak.timedelta.domain.DeltaRepository
import com.darkzodiak.timedelta.domain.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DeltaRepositoryImpl(
    private val alarmEventDao: AlarmEventDao,
    private val appEventDao: AppEventDao,
    private val pendingAlarmDao: PendingAlarmDao,
    private val alarmScheduler: AlarmScheduler
): DeltaRepository {

    override fun getAllPendingAlarms(): Flow<List<PendingAlarm>> {
        return pendingAlarmDao.getAllAlarms()
    }

    override suspend fun addAlarm(alarm: PendingAlarm) {
        val id = pendingAlarmDao.addAlarm(alarm)
        alarmScheduler.schedule(alarm.copy(id = id))
    }

    override suspend fun deleteAlarm(alarm: PendingAlarm) {
        pendingAlarmDao.deleteAlarm(alarm)
        alarmScheduler.cancel(alarm)
    }

    override suspend fun deleteAllPendingAlarms() {
        pendingAlarmDao.clearAllAlarms()
        alarmScheduler.cancelAll()
    }

    override fun getAllEvents(): Flow<List<Event>> {
        val appEvents = appEventDao.getAllEvents().map { it.map { it.toEvent() } }
        val alarmEvents = alarmEventDao.getAllAlarms().map { it.map { it.toEvent() } }

        return appEvents.combine(alarmEvents) { list1, list2 -> list1 + list2 }
    }

    override suspend fun deleteAllEvents() {
        appEventDao.clearAllEvents()
        alarmEventDao.clearAllAlarms()
    }
}