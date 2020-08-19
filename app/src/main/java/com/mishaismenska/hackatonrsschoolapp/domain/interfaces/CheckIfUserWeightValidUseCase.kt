package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface CheckIfUserWeightValidUseCase {
    fun checkIfUserWeightValid(weight: Double): Boolean
}
