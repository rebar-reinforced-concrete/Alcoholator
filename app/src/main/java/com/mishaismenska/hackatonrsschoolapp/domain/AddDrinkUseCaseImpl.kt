package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import javax.inject.Inject

class AddDrinkUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val drinkTypeRepository: DrinkTypeRepository
) : AddDrinkUseCase {
    override suspend fun addDrink(drink: DrinkSubmissionUIModel) {
        appDataRepository.addDrink(
            DrinkDomainModel(
                parseDrinkType(drink.type),
                drink.dateTaken,
                drink.volume,
                drink.eaten
            )
        )
    }

    private fun parseDrinkType(stringRepresentation: String): DrinkPreset {
        val drinks = drinkTypeRepository.provideDrinks()
        val presetValues = DrinkPreset.values()
        for ((index, value) in drinks.withIndex()) {
            if (value == stringRepresentation) return presetValues[index]
        }
        return DrinkPreset.COGNAC
    }
}
