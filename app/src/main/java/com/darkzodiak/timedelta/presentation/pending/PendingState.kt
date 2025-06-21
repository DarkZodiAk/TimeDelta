package com.darkzodiak.timedelta.presentation.pending

import com.darkzodiak.timedelta.data.local.entity.PendingAlarm

data class PendingState(
    val alarms: List<PendingAlarm> = emptyList(),
    val alarmToDelete: PendingAlarm? = null
)
