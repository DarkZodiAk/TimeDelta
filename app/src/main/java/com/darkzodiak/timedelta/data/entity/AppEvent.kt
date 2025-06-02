package com.darkzodiak.timedelta.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val time: Long,
    val message: String
)
