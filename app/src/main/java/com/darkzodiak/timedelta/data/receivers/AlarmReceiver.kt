package com.darkzodiak.timedelta.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.darkzodiak.timedelta.TimeDeltaApp

class AlarmReceiver: BroadcastReceiver() {
    private val module = TimeDeltaApp.appModule
    private val alarmScheduler = module.alarmScheduler
    private val alarmEventDao = module.alarmEventDao
    private val pendingAlarmDao = module.pendingAlarmDao

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}