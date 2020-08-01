package com.mishaismenska.hackatonrsschoolapp.data.models

import java.time.Period

data class UserState(
    val alcoholConcentration: Double,
    val soberTime: Period,
    val behaviour: Behaviours
)
