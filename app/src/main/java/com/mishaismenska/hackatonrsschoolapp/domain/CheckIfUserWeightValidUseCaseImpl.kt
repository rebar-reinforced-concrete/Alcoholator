package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfUserWeightValidUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import javax.inject.Inject

class CheckIfUserWeightValidUseCaseImpl
    @Inject constructor(private val measureSystemsManager: MeasureSystemsManager) : CheckIfUserWeightValidUseCase {
    override fun checkIfUserWeightValid(weight: Double): Boolean {
        val weightInKg = measureSystemsManager.convertUserWeightToMetricIfRequired(weight)
        return weightInKg >= AppConstants.MIN_POSSIBLE_USER_WEIGHT && weightInKg <= AppConstants.MAX_POSSIBLE_USER_WEIGHT
    }
}
