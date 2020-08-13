package com.mishaismenska.hackatonrsschoolapp.data.models

import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Behaviours
import java.time.Duration
import java.time.LocalDateTime

data class UserState(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val behaviour: Behaviours,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
