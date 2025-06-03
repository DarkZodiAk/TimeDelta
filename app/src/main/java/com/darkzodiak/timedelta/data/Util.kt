package com.darkzodiak.timedelta.data

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.darkzodiak.timedelta.data.receivers.BootCompletedReceiver

fun Application.firstLaunchAfterReboot(): Boolean {

    // If check is true, it returns null when there's no such intent
    // If check is false, it just creates an intent
    fun getIntent(check: Boolean): PendingIntent? {
        val intent = Intent(this, BootCompletedReceiver::class.java)
        var flags = PendingIntent.FLAG_IMMUTABLE
        if(check) flags = flags or PendingIntent.FLAG_NO_CREATE
        return PendingIntent.getBroadcast(this, -1, intent, flags)
    }

    if(getIntent(check = true) == null) {
        getIntent(check = false)
        return true
    }
    return false
}