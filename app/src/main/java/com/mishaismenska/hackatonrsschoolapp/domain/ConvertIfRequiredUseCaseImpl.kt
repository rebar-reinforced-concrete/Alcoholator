package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ConvertIfRequiredUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UnitConverter
import javax.inject.Inject

class ConvertIfRequiredUseCaseImpl @Inject constructor(
    private val measureSystemsManager: MeasureSystemsManager,
    private val unitConverter: UnitConverter
) : ConvertIfRequiredUseCase {
    override fun convertToImperialIfRequired(measure: Measure): Measure {
        return if (measureSystemsManager.checkIfMeasureSystemImperial()) {
            when (measure.unit) {
                MeasureUnit.KILOGRAM -> Measure(
                    unitConverter.kgToLb(measure.number.toDouble()),
                    MeasureUnit.POUND
                )
                MeasureUnit.MILLILITER -> Measure(
                    unitConverter.mlToOz(measure.number.toInt()),
                    MeasureUnit.OUNCE
                )
                else -> measure
            }
        } else measure
    }

    override fun convertWeightToMetricIfRequired(value: Double): Double {
        return if (measureSystemsManager.checkIfMeasureSystemImperial()) {
                    unitConverter.lbToKg(value)
            } else value
    }
}
