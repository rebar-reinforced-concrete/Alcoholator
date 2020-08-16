package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateUserNameUseCase
import javax.inject.Inject

class UpdateUserNameUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    UpdateUserNameUseCase {
    override suspend fun updateUserName(newName: String) {
        appDataRepository.setUserName(newName)
    }
}