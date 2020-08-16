package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import javax.inject.Inject

class AddDrinkUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : AddDrinkUseCase {
    override suspend fun addDrink(drink: DrinkSubmissionUIModel) {
        appDataRepository.addDrink(
            DrinkDomainModel(
                drink.type,
                drink.dateTaken,
                drink.volume,
                drink.eaten
            )
        )
    }
}
