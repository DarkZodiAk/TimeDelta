@file:OptIn(ExperimentalMaterial3Api::class)

package com.darkzodiak.timedelta.presentation.setAlarm

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkzodiak.timedelta.domain.AlarmType
import com.darkzodiak.timedelta.presentation.formatDate
import com.darkzodiak.timedelta.presentation.setAlarm.components.DatePickerDialog
import com.darkzodiak.timedelta.presentation.setAlarm.components.InfoRow
import com.darkzodiak.timedelta.presentation.setAlarm.components.TimePickerDialog

@Composable
fun SetAlarmScreenRoot(
    viewModel: SetAlarmViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    SetAlarmScreen(viewModel.state, viewModel::onAction)
}

@Composable
fun SetAlarmScreen(
    state: SetAlarmState,
    onAction: (SetAlarmAction) -> Unit
) {
    var dropdownVisible by rememberSaveable { mutableStateOf(false) }
    var timePickerVisible by rememberSaveable { mutableStateOf(false) }
    var datePickerVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        InfoRow {
            Text("Alarm type: ${state.type}", fontSize = 16.sp)
            OutlinedButton(onClick = { dropdownVisible = true }) {
                Text("Change")

                if(dropdownVisible) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { dropdownVisible = false },
                        modifier = Modifier.clickable { dropdownVisible = true }
                    ) {
                        AlarmType.entries.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.name) },
                                onClick = {
                                    onAction(SetAlarmAction.SetAlarmType(item))
                                    dropdownVisible = false
                                }
                            )
                        }
                    }
                }
            }
        }

        InfoRow {
            Text("Alarm date: ${state.date?.formatDate()}", fontSize = 16.sp)
            OutlinedButton(onClick = { datePickerVisible = true }) {
                Text("Change")
            }
        }

        InfoRow {
            Text("Alarm time: ${state.hours}:${state.minutes}", fontSize = 16.sp)
            OutlinedButton(onClick = { timePickerVisible = true }) {
                Text("Change")
            }
        }

        // TODO(Probably will crash with null date)
        // TODO(What about adding alarm with earlier date than now?)
        Button(onClick = { onAction(SetAlarmAction.AddAlarm) }) {
            Text("Add alarm")
        }

        if(datePickerVisible) {
            DatePickerDialog(
                onConfirm = { onAction(SetAlarmAction.SetDate(it)) },
                onDismiss = { datePickerVisible = false }
            )
        }

        if(timePickerVisible) {
            TimePickerDialog(
                onConfirm = { onAction(SetAlarmAction.SetTime(it)) },
                onDismiss = { timePickerVisible = false }
            )
        }
    }
}

@Preview
@Composable
private fun SetAlarmScreenPreview() {
    SetAlarmScreen(SetAlarmState()) { }
}