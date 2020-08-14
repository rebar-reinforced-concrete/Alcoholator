package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.RemoveDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import javax.inject.Inject

class RemoveDrinkUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : RemoveDrinkUseCase {
    override suspend fun removeDrink(recyclerPosition: Int) {
        appDataRepository.deleteDrink(recyclerPosition)
    }

}
