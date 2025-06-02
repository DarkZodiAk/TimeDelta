package com.darkzodiak.timedelta.data

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.content.getSystemService
import com.darkzodiak.timedelta.data.local.dao.AlarmEventDao
import com.darkzodiak.timedelta.data.local.dao.PendingAlarmDao
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import com.darkzodiak.timedelta.domain.AlarmType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AlarmScheduler(
    private val context: Context,
    private val alarmEventDao: AlarmEventDao,
    private val pendingAlarmDao: PendingAlarmDao
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val handler = Handler(Looper.getMainLooper())
    private val alarmManager by lazy {
        context.getSystemService<AlarmManager>()!!
    }

    @SuppressLint("MissingPermission")
    fun schedule(alarm: PendingAlarm) {
        val intent = getIntentForAlarm(alarm)

        when(alarm.type) {
            AlarmType.INEXACT -> {
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.scheduled, intent)
            }
            AlarmType.INEXACT_WHILE_IDLE -> {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarm.scheduled, intent)
            }
            AlarmType.EXACT -> {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.scheduled, intent)
            }
            AlarmType.EXACT_WHILE_IDLE -> {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarm.scheduled, intent)
            }
            AlarmType.SCHEDULED_WORK -> TODO()
            AlarmType.HANDLER_TASK -> {
                handler.postDelayed(
                    { intent.send() },
                    alarm.scheduled - System.currentTimeMillis()
                )
            }
        }
    }

    fun cancel(alarm: PendingAlarm) {
        when(alarm.type) {
            AlarmType.SCHEDULED_WORK -> TODO()
            AlarmType.HANDLER_TASK -> TODO()
            else -> alarmManager.cancel(getIntentForAlarm(alarm))
        }
    }

    fun cancelAll() {
        scope.launch {
            val alarms = pendingAlarmDao.getAllAlarms().first()
            alarms.forEach {
                alarmManager.cancel(getIntentForAlarm(it))
            }
        }
    }

    private fun getIntentForAlarm(alarm: PendingAlarm): PendingIntent {
        return Intent(context, AlarmReceiver::class.java).run {
            putExtra(ID, alarm.id)
            PendingIntent.getBroadcast(context, alarm.hashCode(), this, PendingIntent.FLAG_IMMUTABLE)
        }
    }

    companion object {
        const val ID = "ID"
    }
}