package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface DetermineMaximalAlcoholConcentrationExceededUseCase {
    fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean
}
