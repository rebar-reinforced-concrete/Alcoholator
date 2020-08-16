package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface CheckIfConcentrationExceededUseCase {
    fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean
}
