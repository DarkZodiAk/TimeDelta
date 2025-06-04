package com.darkzodiak.timedelta.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.darkzodiak.timedelta.presentation.events.EventsScreenRoot
import com.darkzodiak.timedelta.presentation.events.EventsViewModel
import com.darkzodiak.timedelta.presentation.pending.PendingScreenRoot
import com.darkzodiak.timedelta.presentation.pending.PendingViewModel
import com.darkzodiak.timedelta.presentation.setAlarm.SetAlarmScreenRoot
import com.darkzodiak.timedelta.presentation.setAlarm.SetAlarmViewModel
import kotlinx.coroutines.launch

@Composable
fun AppRoot(
    setAlarmViewModel: SetAlarmViewModel,
    eventsViewModel: EventsViewModel,
    pendingViewModel: PendingViewModel
) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TabItem.entries.forEachIndexed { index, tabItem ->
                    NavigationBarItem(
                        selected = index == selectedTabIndex,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                            selectedTabIndex = index
                        },
                        icon = { Icon(tabItem.icon, null) },
                        label = { Text(tabItem.title) }
                    )
                }
            }
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding).fillMaxSize()
        ) { index ->
            when(TabItem.entries[index]) {
                TabItem.SET_ALARM -> SetAlarmScreenRoot(setAlarmViewModel)
                TabItem.EVENTS -> EventsScreenRoot(eventsViewModel)
                TabItem.PENDING -> PendingScreenRoot(pendingViewModel)
            }
        }
    }
}

enum class TabItem(
    val title: String,
    val icon: ImageVector
) {
    SET_ALARM("Set alarm", Icons.Default.AddAlarm),
    EVENTS("Events", Icons.AutoMirrored.Filled.Article),
    PENDING("Pending", Icons.Default.Event)
}