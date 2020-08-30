package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.networking.UserRetrofitService
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetServerExistenceUseCase
import javax.inject.Inject

class GetServerExistenceUseCaseImpl @Inject constructor(private val userRetrofitService: UserRetrofitService) : GetServerExistenceUseCase {
    override suspend fun checkIfUserExists(token: String): Boolean? {
        return userRetrofitService.checkIfUserExists(token).body()
    }
}
