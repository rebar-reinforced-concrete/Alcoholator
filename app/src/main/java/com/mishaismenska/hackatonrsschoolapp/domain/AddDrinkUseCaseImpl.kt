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
        return when (stringRepresentation) {
            drinks[0] -> DrinkPreset.VODKA
            drinks[1] -> DrinkPreset.JINN_TONIC
            drinks[2] -> DrinkPreset.WINE_RED
            drinks[3] -> DrinkPreset.WINE_WHITE
            drinks[4] -> DrinkPreset.WINE_RED_CHEAP
            drinks[5] -> DrinkPreset.WINE_WHITE_CHEAP
            drinks[6] -> DrinkPreset.FRUIT_WINE_CHEAP
            drinks[7] -> DrinkPreset.BEER_DARK
            drinks[8] -> DrinkPreset.BEER_LIGHT
            drinks[9] -> DrinkPreset.BEER_DARK_CHEAP
            drinks[10] -> DrinkPreset.BEER_LIGHT_CHEAP
            drinks[11] -> DrinkPreset.MOONSHINE
            drinks[12] -> DrinkPreset.SPIRIT
            drinks[13] -> DrinkPreset.WHISKEY
            drinks[14] -> DrinkPreset.WHISKEY_CHEAP
            drinks[15] -> DrinkPreset.LIQUOR
            drinks[16] -> DrinkPreset.CHAMPAGNE
            else -> DrinkPreset.COGNAC
        }
    }
}
