package com.mishaismenska.hackatonrsschoolapp.presentation.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPresets
import java.time.LocalDateTime

data class DrinkUIModel(val type: DrinkPresets,
                   val date: LocalDateTime,
                   val volume: Measure)
