package com.mishaismenska.hackatonrsschoolapp.domain.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPresets
import java.time.LocalDateTime

data class DrinkDomainModel(
    val type: DrinkPresets,
    val date: LocalDateTime,
    val volume: Measure,
    val eaten: Boolean
)
