package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface MeasureSystemsManager {
    fun checkIfMeasureSystemImperial(): Boolean
    fun convertUserWeightToImperialIfRequired(weight: Double): Double
    fun convertUserWeightToMetricIfRequired(weight: Double): Double
}
