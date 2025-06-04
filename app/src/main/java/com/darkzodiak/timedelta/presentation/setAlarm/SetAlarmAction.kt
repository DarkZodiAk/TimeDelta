@file:OptIn(ExperimentalMaterial3Api::class)

package com.darkzodiak.timedelta.presentation.setAlarm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.darkzodiak.timedelta.domain.AlarmType

sealed interface SetAlarmAction {
    object AddAlarm: SetAlarmAction
    data class SetAlarmType(val type: AlarmType): SetAlarmAction
    data class SetDate(val date: Long): SetAlarmAction
    data class SetTime(val time: TimePickerState): SetAlarmAction
}