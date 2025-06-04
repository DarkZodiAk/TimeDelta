package com.darkzodiak.timedelta.presentation.setAlarm

import com.darkzodiak.timedelta.domain.AlarmType
import java.util.Calendar

data class SetAlarmState(
    val setAtDateTime: Boolean = true,
    val date: Long? = null,
    val hours: Int = 0,
    val minutes: Int = 0,
    val type: AlarmType = AlarmType.INEXACT
) {
    fun getTimestamp(): Long {
        if(date == null) return 0L
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }
}