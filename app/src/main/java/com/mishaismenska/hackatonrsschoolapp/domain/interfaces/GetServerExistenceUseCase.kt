package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface GetServerExistenceUseCase {
    suspend fun checkIfUserExists(token: String): Boolean?
}
