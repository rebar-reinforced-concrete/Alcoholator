package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.DrinkPresets
import java.time.LocalDateTime

data class Drink(val type: DrinkPresets, val date: LocalDateTime, val volume: Measure, val eaten: Boolean)
