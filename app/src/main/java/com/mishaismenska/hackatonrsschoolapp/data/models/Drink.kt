package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import java.time.LocalDate

data class Drink(val type: DrinkType, val date: LocalDate, val volume: Measure)
