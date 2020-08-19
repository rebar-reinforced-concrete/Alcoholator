package com.mishaismenska.hackatonrsschoolapp.domain.logic

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UnitConverter
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import java.util.Locale
import javax.inject.Inject

class MeasureSystemsManagerImpl @Inject constructor(private val unitConverter: UnitConverter) : MeasureSystemsManager {
    override fun checkIfMeasureSystemImperial(): Boolean = Locale.getDefault().country in AppConstants.imperialLocales

    override fun convertUserWeightToImperialIfRequired(weight: Double): Double {
        return if (checkIfMeasureSystemImperial()) {
            unitConverter.kgToLb(weight)
        } else weight
    }

    override fun convertUserWeightToMetricIfRequired(weight: Double): Double {
        return if (checkIfMeasureSystemImperial()) {
            unitConverter.lbToKg(weight)
        } else weight
    }
}
