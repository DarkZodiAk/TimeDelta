package com.darkzodiak.timedelta.presentation.pending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzodiak.timedelta.domain.DeltaRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PendingViewModel(
    private val deltaRepository: DeltaRepository
): ViewModel() {

    var state by mutableStateOf(PendingState())
        private set

    init {
        deltaRepository.getAllPendingAlarms()
            .map { it.sortedBy { it.scheduled } }
            .onEach { state = state.copy(alarms = it) }
            .launchIn(viewModelScope)
    }

    fun onAction(action: PendingAction) {
        when(action) {
            PendingAction.DeleteAllAlarms -> {
                viewModelScope.launch {
                    deltaRepository.deleteAllPendingAlarms()
                }
            }
            PendingAction.DismissDeletion -> {
                state = state.copy(alarmToDelete = null)
            }
            is PendingAction.TryDeleteAlarm -> {
                if(state.alarmToDelete == action.alarm) {
                    viewModelScope.launch {
                        deltaRepository.deleteAlarm(action.alarm)
                    }
                    state = state.copy(alarmToDelete = null)
                } else {
                    state = state.copy(alarmToDelete = action.alarm)
                }
            }
        }
    }
}