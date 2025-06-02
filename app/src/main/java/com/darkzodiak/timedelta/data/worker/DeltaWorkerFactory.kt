package com.darkzodiak.timedelta.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.darkzodiak.timedelta.di.DeltaModule

class DeltaWorkerFactory(
    private val module: DeltaModule
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            AlarmWorker::class.java.name ->
                AlarmWorker(appContext, workerParameters, module.pendingAlarmDao, module.alarmEventDao)
            else -> null
        }
    }
}