package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import java.time.LocalDateTime

data class Drink(
    val type: DrinkType,
    val date: LocalDateTime,
    val volume: Measure,
    val eaten: Boolean
)
