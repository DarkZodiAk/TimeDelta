package com.darkzodiak.timedelta.domain

enum class AlarmType {
    INEXACT, INEXACT_WHILE_IDLE,
    EXACT, EXACT_WHILE_IDLE,
    SCHEDULED_WORK,
    HANDLER_TASK
}