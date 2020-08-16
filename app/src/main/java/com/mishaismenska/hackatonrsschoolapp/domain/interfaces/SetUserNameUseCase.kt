package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface SetUserNameUseCase {
    suspend fun updateUserName(newName: String)
}
