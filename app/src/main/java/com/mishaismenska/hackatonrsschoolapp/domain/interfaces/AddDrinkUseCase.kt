package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel

interface AddDrinkUseCase {
    suspend fun addDrink(drink: DrinkSubmissionUIModel)
}
