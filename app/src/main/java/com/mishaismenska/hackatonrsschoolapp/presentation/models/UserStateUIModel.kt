package com.mishaismenska.hackatonrsschoolapp.presentation.models

import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import java.time.Duration
import java.time.LocalDateTime

data class UserStateUIModel(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val behaviour: Behaviours
)
