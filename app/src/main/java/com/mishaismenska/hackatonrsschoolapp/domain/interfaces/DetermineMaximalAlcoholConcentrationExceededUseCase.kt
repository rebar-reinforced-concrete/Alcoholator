package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface DetermineMaximalAlcoholConcentrationExceededUseCase {
    fun determineIfUserStillCanDrink(alcoholConcentration: Double): Boolean
}
