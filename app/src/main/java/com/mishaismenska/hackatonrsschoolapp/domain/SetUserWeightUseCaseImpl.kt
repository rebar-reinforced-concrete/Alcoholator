package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserWeightUseCase
import javax.inject.Inject

class SetUserWeightUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val measureSystemsManager: MeasureSystemsManager
) : SetUserWeightUseCase {
    override suspend fun setUserWeight(weight: Double) {
        appDataRepository.setWeight(measureSystemsManager.convertUserWeightToMetricIfRequired(weight))
    }
}
