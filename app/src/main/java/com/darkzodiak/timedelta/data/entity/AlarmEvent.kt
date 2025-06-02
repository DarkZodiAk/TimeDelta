package com.darkzodiak.timedelta.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val type: String,
    val scheduled: Long,
    val firedAt: Long
)
