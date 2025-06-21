package com.darkzodiak.timedelta.data

import android.content.Context
import androidx.core.content.edit
import com.darkzodiak.timedelta.data.local.dao.AppEventDao
import com.darkzodiak.timedelta.data.local.entity.AppEventEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppEventLogger(
    context: Context,
    private val appEventDao: AppEventDao
) {
    private val pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val scope = CoroutineScope(Dispatchers.IO)

    fun launch() {
        val curTime = System.currentTimeMillis()
        val prevTime = pref.getLong(LAST_TIME, 0L)
        saveCurrentTime(curTime)

        scope.launch {
            if(prevTime != 0L) {
                appEventDao.addEvent(AppEventEntity(time = prevTime, message = "App closed/terminated"))
            }
            appEventDao.addEvent(AppEventEntity(time = curTime, message = "App launched"))

            while(true) {
                delay(1000L)
                saveCurrentTime(System.currentTimeMillis())
            }
        }
    }

    private fun saveCurrentTime(time: Long) {
        pref.edit {
            putLong(LAST_TIME, time)
        }
    }

    companion object {
        const val NAME = "LOG"
        const val LAST_TIME = "TIME"
    }
}