package com.darkzodiak.timedelta.data.local

import androidx.room.TypeConverter
import com.darkzodiak.timedelta.domain.AlarmType

class Converters {
    @TypeConverter
    fun toAlarmType(value: String): AlarmType {
        return AlarmType.valueOf(value)
    }

    @TypeConverter
    fun fromAlarmType(value: AlarmType): String {
        return value.name
    }
}