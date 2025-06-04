package com.darkzodiak.timedelta.presentation.setAlarm.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    val confirmAvailable by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    // TODO(Dialog looks wrong, needs fix)
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis!!)
                    onDismiss()
                },
                enabled = confirmAvailable
            ) {
                Text("OK")
            }
        },
        text = {
            DatePicker(datePickerState)
        }
    )
}