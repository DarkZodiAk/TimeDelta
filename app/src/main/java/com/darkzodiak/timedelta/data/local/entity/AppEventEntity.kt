package com.darkzodiak.timedelta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val time: Long,
    val message: String
)
