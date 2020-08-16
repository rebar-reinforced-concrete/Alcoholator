package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserWeightUseCase
import javax.inject.Inject

class SetUserWeightUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : SetUserWeightUseCase {
    override suspend fun setUserWeight(weight: Int) {
        appDataRepository.setWeight(weight)
    }
}
