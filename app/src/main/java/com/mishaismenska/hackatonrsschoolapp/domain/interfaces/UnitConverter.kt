package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface UnitConverter {
    fun kgToLb(kgs: Double): Double
    fun lbToKg(lbs: Double): Double
    fun mlToOz(mls: Int): Double
}
