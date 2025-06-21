package com.darkzodiak.timedelta.presentation.events

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkzodiak.timedelta.domain.AlarmType
import com.darkzodiak.timedelta.domain.Event
import com.darkzodiak.timedelta.presentation.formatTimeNonZoned
import com.darkzodiak.timedelta.presentation.formatTimestamp

@Composable
fun EventRow(
    event: Event,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.DarkGray)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if(event is Event.AlarmEvent) {
                Text("Alarm: ${event.type}")
                Text("Scheduled: ${event.scheduled.formatTimestamp()}")
                Text("Fired at:      ${event.firedAt.formatTimestamp()}")
                Text("Difference: ${event.difference.formatTimeNonZoned()}")
            } else if(event is Event.AppEvent) {
                Text("App event: ${event.message}")
                Text("Fired at: ${event.firedAt.formatTimestamp()}")
            }
        }
    }
}

@Preview
@Composable
private fun EventRowPreview() {
    EventRow(event = Event.AlarmEvent(null, AlarmType.INEXACT, 174L, 1749L, difference = 86L - 17L))
}