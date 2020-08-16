package com.mishaismenska.hackatonrsschoolapp.domain.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import java.time.LocalDateTime

data class DrinkDomainModel(
    val type: DrinkPreset,
    val dateTaken: LocalDateTime,
    val volume: Measure,
    val eaten: Boolean
)
