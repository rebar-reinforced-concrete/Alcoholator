package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.data.UnitConverter
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ConvertIfRequiredUseCase
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants.imperialLocales
import java.util.*
import javax.inject.Inject

class ConvertIfRequiredUseCaseImpl @Inject constructor() : ConvertIfRequiredUseCase {
    override fun convertToImperialIfRequired(measure: Measure): Measure {
        return if (Locale.getDefault().country in imperialLocales) {
            when (measure.unit) {
                MeasureUnit.KILOGRAM -> Measure(
                    UnitConverter.kgToLb(measure.number.toDouble()),
                    MeasureUnit.POUND
                )
                MeasureUnit.MILLILITER -> Measure(
                    UnitConverter.mlToOz(measure.number.toInt()),
                    MeasureUnit.OUNCE
                )
                else -> measure // for future units
            }
        }
        else measure
    }

    override fun convertWeightToMetricIfRequired(value: Double): Double {
        return if (Locale.getDefault().country in imperialLocales) {
                    UnitConverter.lbToKg(value)
            }
            else value
    }
}