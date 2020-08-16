package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import kotlinx.coroutines.flow.Flow

interface GetDrinksUseCase {
    suspend fun getDrinks(): Flow<List<DrinkUIModel>>
}
