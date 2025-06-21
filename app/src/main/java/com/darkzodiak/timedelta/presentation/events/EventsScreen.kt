package com.darkzodiak.timedelta.presentation.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkzodiak.timedelta.domain.AlarmType
import com.darkzodiak.timedelta.domain.Event

@Composable
fun EventsScreenRoot(
    viewModel: EventsViewModel
) {
    EventsScreen(
        events = viewModel.events.collectAsState().value,
        onDeleteAll = viewModel::deleteAllEvents
    )
}

@Composable
fun EventsScreen(
    events: List<Event>,
    onDeleteAll: () -> Unit
) {
    var clearDialogVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            OutlinedButton(onClick = { clearDialogVisible = true }) {
                Text("Clear all events")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(events, key = { it.id!! }) { event ->
                EventRow(event)
            }
        }
    }

    if(clearDialogVisible) {
        AlertDialog(
            onDismissRequest = { clearDialogVisible = false },
            dismissButton = {
                Button(onClick = { clearDialogVisible = false }) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        clearDialogVisible = false
                        onDeleteAll()
                    }
                ) {
                    Text("Clear")
                }
            },
            title = { Text("All events will be cleared") },
            text = { Text("This operation can't be undone") }
        )
    }
}

@Preview
@Composable
private fun EventsScreenPreview() {
    EventsScreen(
        listOf(
            Event.AlarmEvent(
                null,
                AlarmType.INEXACT,
                1749013085000L,
                1749013391000L,
                306000L),
            Event.AppEvent(
                null,
                1749013421000L,
                "Terminated"
            )
        ),
    ) {}
}