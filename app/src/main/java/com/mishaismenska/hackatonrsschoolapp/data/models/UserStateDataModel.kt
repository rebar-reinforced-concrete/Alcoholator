package com.mishaismenska.hackatonrsschoolapp.data.models

import java.time.Duration
import java.time.LocalDateTime

data class UserStateDataModel(
    val alcoholConcentration: Double,
    val alcoholDepletionDuration: Duration,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
