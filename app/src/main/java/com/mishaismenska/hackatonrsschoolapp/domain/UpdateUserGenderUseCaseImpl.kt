package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserGenderUseCase
import javax.inject.Inject

class UpdateUserGenderUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    SetUserGenderUseCase {
    override suspend fun setUserGender(newGender: Int) {
        appDataRepository.setGender(newGender)
    }
}
