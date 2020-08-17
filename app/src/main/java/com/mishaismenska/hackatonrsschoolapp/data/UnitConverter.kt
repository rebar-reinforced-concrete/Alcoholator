package com.mishaismenska.hackatonrsschoolapp.data

object UnitConverter {
    private fun roundTo2Decimal(value: Double): Double {
        return String.format("%.2f", value).toDouble()
    }

    fun kgToLb(kgs: Double) = roundTo2Decimal(kgs * 2.2)
    fun lbToKg(lbs: Double) = roundTo2Decimal(lbs / 2.2)
    fun mlToOz(mls: Int) = roundTo2Decimal(mls / 29.547)
    fun ozToMl(ozs: Double) = (ozs * 29.547).toInt()
}
