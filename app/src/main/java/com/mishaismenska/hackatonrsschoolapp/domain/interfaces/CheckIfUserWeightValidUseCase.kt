package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface CheckIfUserWeightValidUseCase {
    fun checkIfUserWeightValid(weightInKg: Int): Boolean
}
