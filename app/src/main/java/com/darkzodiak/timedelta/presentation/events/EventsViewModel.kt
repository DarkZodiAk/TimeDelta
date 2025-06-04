package com.darkzodiak.timedelta.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzodiak.timedelta.domain.DeltaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventsViewModel(
    private val deltaRepository: DeltaRepository
): ViewModel() {

    val events = deltaRepository.getAllEvents()
        .map { it.sortedByDescending { it.firedAt } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteAllEvents() {
        viewModelScope.launch {
            deltaRepository.deleteAllEvents()
        }
    }
}