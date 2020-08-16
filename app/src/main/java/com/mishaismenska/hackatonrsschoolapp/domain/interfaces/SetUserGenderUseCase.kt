package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface SetUserGenderUseCase {
    suspend fun setUserGender(newGender: Int)
}
