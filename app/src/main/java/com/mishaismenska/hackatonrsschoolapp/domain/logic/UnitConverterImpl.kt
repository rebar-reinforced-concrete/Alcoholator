package com.mishaismenska.hackatonrsschoolapp.domain.logic

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UnitConverter
import javax.inject.Inject

class UnitConverterImpl @Inject constructor() : UnitConverter {
    private fun roundTo2Decimal(value: Double): Double {
        return String.format("%.2f", value).toDouble()
    }

    override fun kgToLb(kgs: Double) = roundTo2Decimal(kgs * 2.2)
    override fun lbToKg(lbs: Double) = roundTo2Decimal(lbs / 2.2)
    override fun mlToOz(mls: Int) = roundTo2Decimal(mls / 29.547)
}
