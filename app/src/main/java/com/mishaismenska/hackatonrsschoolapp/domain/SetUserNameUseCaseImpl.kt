package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserNameUseCase
import javax.inject.Inject

class SetUserNameUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    SetUserNameUseCase {
    override suspend fun updateUserName(newName: String) {
        appDataRepository.setUserName(newName)
    }
}
