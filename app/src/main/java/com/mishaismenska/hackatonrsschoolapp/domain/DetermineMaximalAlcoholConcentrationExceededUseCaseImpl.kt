package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DetermineMaximalAlcoholConcentrationExceededUseCase
import javax.inject.Inject

//Second best class regarding solid principles and with the best name
class DetermineMaximalAlcoholConcentrationExceededUseCaseImpl @Inject constructor(private val calculationManager: CalculationManager) : DetermineMaximalAlcoholConcentrationExceededUseCase { override fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean = calculationManager.determineIfUserCanDrink(alcoholConcentration) }

