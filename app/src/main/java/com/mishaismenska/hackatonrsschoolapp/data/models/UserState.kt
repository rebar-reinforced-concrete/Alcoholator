package com.mishaismenska.hackatonrsschoolapp.data.models

import java.time.Duration

data class UserState(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val behaviour: Behaviours
)
