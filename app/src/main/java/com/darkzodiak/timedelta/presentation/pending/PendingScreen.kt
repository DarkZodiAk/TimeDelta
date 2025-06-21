package com.darkzodiak.timedelta.presentation.pending

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PendingScreenRoot(
    viewModel: PendingViewModel
) {
    PendingScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}


@Composable
fun PendingScreen(
    state: PendingState,
    onAction: (PendingAction) -> Unit
) {
    var clearDialogVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            OutlinedButton(
                onClick = {
                    clearDialogVisible = true
                    onAction(PendingAction.DismissDeletion)
                }
            ) {
                Text("Clear all alarms")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.alarms, key = { it.id!! }) { alarm ->
                PendingAlarmRow(
                    alarm = alarm,
                    confirmDeletion = alarm.id == state.alarmToDelete?.id,
                    onTryDeleteAlarm = {
                        onAction(PendingAction.TryDeleteAlarm(alarm))
                    }
                )
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
                        onAction(PendingAction.DeleteAllAlarms)
                    }
                ) {
                    Text("Clear")
                }
            },
            title = { Text("All pending alarms will be cleared") },
            text = { Text("This operation can't be undone") }
        )
    }
}

@Preview
@Composable
private fun PendingScreenPreview() {
    PendingScreen(
        state = PendingState(),
        onAction = { }
    )
}