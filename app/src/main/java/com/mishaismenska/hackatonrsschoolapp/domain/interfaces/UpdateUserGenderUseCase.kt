package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface UpdateUserGenderUseCase {
    suspend fun updateGender(newGender: Int)
}