@file:OptIn(ExperimentalMaterial3Api::class)

package com.darkzodiak.timedelta.presentation.setAlarm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzodiak.timedelta.data.local.entity.PendingAlarm
import com.darkzodiak.timedelta.domain.DeltaRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SetAlarmViewModel(
    private val deltaRepository: DeltaRepository
): ViewModel() {

    var state by mutableStateOf(SetAlarmState())
        private set

    private val channel = Channel<String>()
    val uiMessage = channel.receiveAsFlow()

    fun onAction(action: SetAlarmAction) {
        when(action) {
            SetAlarmAction.AddAlarm -> {
                viewModelScope.launch {
                    deltaRepository.addAlarm(
                        PendingAlarm(null, state.type, state.getTimestamp())
                    )
                    channel.send("Alarm has been successfully added")
                }
            }
            is SetAlarmAction.SetAlarmType -> {
                state = state.copy(type = action.type)
            }
            is SetAlarmAction.SetDate -> {
                state = state.copy(date = action.date)
            }
            is SetAlarmAction.SetTime -> {
                state = state.copy(
                    hours = action.time.hour,
                    minutes = action.time.minute
                )
            }
        }
    }
}