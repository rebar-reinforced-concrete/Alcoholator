package com.mishaismenska.hackatonrsschoolapp.interfaces

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState

interface CalculationManager {
    fun determineState(age: Int, weight: Measure, gender: Gender, drinks: List<Drink>): UserState
}
