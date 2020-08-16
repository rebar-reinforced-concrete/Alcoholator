package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfConcentrationExceededUseCase
import javax.inject.Inject

// Second best class regarding solid principles and with the best name
class CheckIfMaxConcentrationExceededUseCaseImpl
    @Inject constructor(private val calculationManager: CalculationManager) : CheckIfConcentrationExceededUseCase {
        override fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean = calculationManager.determineIfUserCanDrink(alcoholConcentration)
}
