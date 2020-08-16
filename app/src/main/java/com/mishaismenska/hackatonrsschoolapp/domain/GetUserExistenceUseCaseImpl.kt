package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserExistenceUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GetUserExistenceUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetUserExistenceUseCase {
    override suspend fun checkExistence(): Boolean {
        val users = appDataRepository.getUser()
        var userLength = 0
        users.take(1).collect {
            userLength = it.size
        }
        return userLength == 0
    }
}