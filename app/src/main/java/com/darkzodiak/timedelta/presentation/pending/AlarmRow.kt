package com.darkzodiak.timedelta.presentation.pending

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import com.darkzodiak.timedelta.domain.AlarmType
import com.darkzodiak.timedelta.presentation.formatTimestamp

@Composable
fun PendingAlarmRow(
    alarm: PendingAlarm,
    confirmDeletion: Boolean,
    onTryDeleteAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.DarkGray)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text("Alarm: ${alarm.type}")
            Text("Scheduled: ${alarm.scheduled.formatTimestamp()}")
        }

        IconButton(onClick = onTryDeleteAlarm) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = if(confirmDeletion) Color.Red else Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun PendingAlarmRowPreview() {
    PendingAlarmRow(
        alarm = PendingAlarm(null, AlarmType.INEXACT, 10000000000L),
        confirmDeletion = true,
        onTryDeleteAlarm = { }
    )
    
}