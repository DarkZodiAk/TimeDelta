package com.darkzodiak.timedelta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darkzodiak.timedelta.domain.AlarmType
import com.darkzodiak.timedelta.domain.AlarmType.EXACT
import com.darkzodiak.timedelta.domain.AlarmType.EXACT_WHILE_IDLE
import com.darkzodiak.timedelta.domain.AlarmType.INEXACT
import com.darkzodiak.timedelta.domain.AlarmType.INEXACT_WHILE_IDLE

@Entity
data class PendingAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val type: AlarmType,
    val scheduled: Long
) {
    val isSystemAlarm: Boolean
        get() = when(type) {
            INEXACT, INEXACT_WHILE_IDLE, EXACT, EXACT_WHILE_IDLE -> true
            else -> false
        }
}