package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import androidx.lifecycle.LiveData
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel

interface GetDrinksUseCase {
    suspend fun getDrinks(): LiveData<List<DrinkUIModel>>
}
