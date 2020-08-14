package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateWeightUseCase
import javax.inject.Inject

class UpdateWeightUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository): UpdateWeightUseCase {
    override suspend fun updateWeight(weight: Int) {
        appDataRepository.updateWeight(weight)
    }
}
