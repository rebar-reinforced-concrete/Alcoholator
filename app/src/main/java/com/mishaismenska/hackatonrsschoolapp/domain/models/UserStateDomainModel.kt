package com.mishaismenska.hackatonrsschoolapp.domain.models

import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import java.time.Duration
import java.time.LocalDateTime

data class UserStateDomainModel(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
