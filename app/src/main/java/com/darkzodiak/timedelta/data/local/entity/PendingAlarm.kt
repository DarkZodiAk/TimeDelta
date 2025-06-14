package com.darkzodiak.timedelta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darkzodiak.timedelta.domain.AlarmType

@Entity
data class PendingAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val type: AlarmType,
    val scheduled: Long
)