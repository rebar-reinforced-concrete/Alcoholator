package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserExistenceUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take

class GetUserExistenceUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetUserExistenceUseCase {
    override suspend fun checkIfUserExists(): Boolean {
        val users = appDataRepository.getUser()
        var recordsAmount = 0
        users.take(1).collect {
            recordsAmount = it.size
        }
        return recordsAmount != 0
    }
}
