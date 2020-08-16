package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface UpdateUserNameUseCase {
    suspend fun updateUserName(newName: String)
}