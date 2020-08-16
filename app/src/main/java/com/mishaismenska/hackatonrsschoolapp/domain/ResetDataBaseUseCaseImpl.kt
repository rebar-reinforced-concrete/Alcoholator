package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ResetDataBaseUseCase
import javax.inject.Inject

class ResetDataBaseUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : ResetDataBaseUseCase {
    override suspend fun resetDataBase() = appDataRepository.reset()
}
