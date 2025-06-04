package com.darkzodiak.timedelta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.darkzodiak.timedelta.domain.DeltaRepository
import com.darkzodiak.timedelta.presentation.AppRoot
import com.darkzodiak.timedelta.presentation.events.EventsViewModel
import com.darkzodiak.timedelta.presentation.pending.PendingViewModel
import com.darkzodiak.timedelta.presentation.setAlarm.SetAlarmViewModel
import com.darkzodiak.timedelta.ui.theme.TimeDeltaTheme

class MainActivity : ComponentActivity() {

    private lateinit var repository: DeltaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = TimeDeltaApp.appModule.deltaRepository

        val factory = getFactory(repository)

        val setAlarmViewModel = ViewModelProvider(this, factory)[SetAlarmViewModel::class.java]
        val eventsViewModel = ViewModelProvider(this, factory)[EventsViewModel::class.java]
        val pendingViewModel = ViewModelProvider(this, factory)[PendingViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TimeDeltaTheme {
                AppRoot(
                    setAlarmViewModel,
                    eventsViewModel,
                    pendingViewModel
                )
            }
        }
    }
}

fun getFactory(repository: DeltaRepository) = viewModelFactory {
    addInitializer(SetAlarmViewModel::class) { SetAlarmViewModel(repository) }
    addInitializer(EventsViewModel::class) { EventsViewModel(repository) }
    addInitializer(PendingViewModel::class) { PendingViewModel(repository) }
}