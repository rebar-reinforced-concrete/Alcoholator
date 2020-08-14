package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel

interface RemoveDrinkUseCase {
    suspend fun removeDrink(recyclerPosition: Int)
}
