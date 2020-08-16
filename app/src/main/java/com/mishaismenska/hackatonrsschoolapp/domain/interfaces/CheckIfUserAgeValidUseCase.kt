package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface CheckIfUserAgeValidUseCase {
    fun checkIfUserAgeValid(age: Int): Boolean
}
