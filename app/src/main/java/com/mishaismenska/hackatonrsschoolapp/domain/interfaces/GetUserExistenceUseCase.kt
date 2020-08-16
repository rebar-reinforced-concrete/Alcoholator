package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface GetUserExistenceUseCase {
    suspend fun checkExistence(): Boolean
}