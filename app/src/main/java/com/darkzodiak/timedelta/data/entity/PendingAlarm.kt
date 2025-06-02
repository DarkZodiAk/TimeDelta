package com.darkzodiak.timedelta.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PendingAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val type: String,
    val time: Long
)