package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import android.icu.util.Measure

interface ConvertIfRequiredUseCase {
    fun convertToImperialIfRequired(measure: Measure): Measure
    fun convertWeightToMetricIfRequired(value: Double): Double
}