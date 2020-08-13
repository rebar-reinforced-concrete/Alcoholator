package com.mishaismenska.hackatonrsschoolapp.data.models

import java.time.Duration
import java.time.LocalDateTime

data class UserState(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val behaviour: Behaviours,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
