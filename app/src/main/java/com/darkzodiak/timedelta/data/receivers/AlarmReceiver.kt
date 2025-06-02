package com.darkzodiak.timedelta.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.darkzodiak.timedelta.TimeDeltaApp
import com.darkzodiak.timedelta.data.AlarmScheduler
import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmReceiver: BroadcastReceiver() {
    private val firedAt = System.currentTimeMillis()

    private val module = TimeDeltaApp.appModule
    private val alarmEventDao = module.alarmEventDao
    private val pendingAlarmDao = module.pendingAlarmDao

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) return

        val id = intent.getLongExtra(AlarmScheduler.ID, -1)
        scope.launch {
            val alarm = pendingAlarmDao.getAlarmById(id) ?: return@launch
            alarmEventDao.addAlarm(
                AlarmEventEntity(null, alarm.type, alarm.scheduled, firedAt)
            )
            withContext(NonCancellable) {
                pendingAlarmDao.deleteAlarm(alarm)
            }
        }
    }
}