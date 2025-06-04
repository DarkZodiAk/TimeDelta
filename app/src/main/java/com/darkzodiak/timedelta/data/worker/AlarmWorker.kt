package com.darkzodiak.timedelta.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import com.darkzodiak.timedelta.domain.AlarmType
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class AlarmWorker(
    context: Context,
    private val params: WorkerParameters,
    private val pendingAlarmDao: PendingAlarmDao,
    private val alarmEventDao: AlarmEventDao
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val firedAt = System.currentTimeMillis()
        val id = params.inputData.getLong(AlarmScheduler.ID, -1L)

        val alarm = pendingAlarmDao.getAlarmById(id) ?: return Result.success()

        alarmEventDao.addAlarm(
            AlarmEventEntity(null, AlarmType.SCHEDULED_WORK, alarm.scheduled, firedAt, firedAt - alarm.scheduled)
        )
        withContext(NonCancellable) {
            pendingAlarmDao.deleteAlarm(alarm)
        }

        return Result.success()
    }
}