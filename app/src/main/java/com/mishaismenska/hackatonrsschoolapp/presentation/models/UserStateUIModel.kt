package com.mishaismenska.hackatonrsschoolapp.presentation.models

import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behavior
import java.time.Duration

data class UserStateUIModel(
    val alcoholConcentration: Double,
    val alcoholDepletionDuration: Duration,
    val behavior: Behavior
)
