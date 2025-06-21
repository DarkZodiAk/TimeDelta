package com.darkzodiak.timedelta.presentation.pending

import com.darkzodiak.timedelta.data.local.entity.PendingAlarm

sealed interface PendingAction {
    object DeleteAllAlarms: PendingAction
    data class TryDeleteAlarm(val alarm: PendingAlarm): PendingAction
    object DismissDeletion: PendingAction
}