package com.darkzodiak.timedelta.data.worker

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import java.util.UUID
import java.util.concurrent.TimeUnit

class DelayedAlarmRunner(
    private val workManager: WorkManager
) {
    fun schedule(alarm: PendingAlarm) {
        val workRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
            .setInputData(Data.Builder()
                .putLong(AlarmScheduler.ID, alarm.id!!)
                .build()
            ).setId(getUUIDFromId(alarm.id))
            .setInitialDelay(alarm.scheduled - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueue(workRequest)
    }

    fun cancel(alarm: PendingAlarm) {
        workManager.cancelWorkById(getUUIDFromId(alarm.id!!))
    }

    private fun getUUIDFromId(id: Long) = UUID(0L, id)
}