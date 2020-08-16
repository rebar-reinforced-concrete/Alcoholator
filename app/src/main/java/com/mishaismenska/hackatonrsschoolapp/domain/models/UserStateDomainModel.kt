package com.mishaismenska.hackatonrsschoolapp.domain.models

import java.time.Duration
import java.time.LocalDateTime

data class UserStateDomainModel(
    val alcoholConcentration: Double,
    val alcoholDepletionDuration: Duration,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
