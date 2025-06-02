package com.darkzodiak.timedelta.data

import com.darkzodiak.timedelta.data.local.entity.AlarmEventEntity
import com.darkzodiak.timedelta.data.local.entity.AppEventEntity
import com.darkzodiak.timedelta.domain.Event

fun AlarmEventEntity.toEvent() = Event.AlarmEvent(id, type, scheduled, firedAt)

fun AppEventEntity.toEvent() = Event.AppEvent(id, time, message)
