package com.darkzodiak.timedelta.data.local

import androidx.room.TypeConverters
import com.darkzodiak.timedelta.domain.AlarmType

class Converters {
    @TypeConverters
    fun toAlarmType(value: String): AlarmType {
        return AlarmType.valueOf(value)
    }

    @TypeConverters
    fun fromAlarmType(value: AlarmType): String {
        return value.name
    }
}