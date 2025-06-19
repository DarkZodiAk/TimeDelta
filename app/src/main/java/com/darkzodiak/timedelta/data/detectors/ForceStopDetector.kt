package com.darkzodiak.timedelta.data.detectors

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_NO_CREATE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.getSystemService
import kotlin.time.Duration.Companion.days


class ForceStopDetector(
    private val context: Context
) {
    fun wasForceStopped(): Boolean {
        var flags = FLAG_NO_CREATE
        if (Build.VERSION.SDK_INT >= 31) {
            flags = flags or FLAG_MUTABLE
        }
        val pendingIntent = getPendingIntent(context, flags)

        if(pendingIntent == null) {
            setAlarm(context)
            return true
        }
        return false
    }


    private fun getPendingIntent(context: Context, flags: Int): PendingIntent? {
        val intent = Intent(context, Receiver::class.java)
        return PendingIntent.getBroadcast(context, -10, intent, flags)
    }

    private fun setAlarm(context: Context) {
        val alarmManager = context.getSystemService<AlarmManager>()!!

        var flags = FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= 31) {
            flags = flags or FLAG_MUTABLE
        }
        val pendingIntent = getPendingIntent(context, flags)

        val triggerAt: Long = System.currentTimeMillis() + (10.days * 365).inWholeMilliseconds
        alarmManager.set(RTC_WAKEUP, triggerAt, pendingIntent!!)
    }

    private object Receiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {}
    }
}