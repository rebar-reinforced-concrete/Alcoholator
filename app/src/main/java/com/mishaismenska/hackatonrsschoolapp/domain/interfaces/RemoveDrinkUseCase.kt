package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface RemoveDrinkUseCase {
    suspend fun removeDrink(recyclerPosition: Int)
}
