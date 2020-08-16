package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateUserGenderUseCase
import javax.inject.Inject

class UpdateUserGenderUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    UpdateUserGenderUseCase {
    override suspend fun updateGender(newGender: Int) {
        appDataRepository.updateGender(newGender)
    }
}