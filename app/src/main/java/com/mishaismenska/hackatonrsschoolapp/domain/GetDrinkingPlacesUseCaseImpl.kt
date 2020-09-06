package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinkingPlacesUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDrinkingPlacesUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val drinkTypeRepository: DrinkTypeRepository
) :
    GetDrinkingPlacesUseCase {
    override suspend fun getPlaces(): Flow<List<LocationUIModel?>> {
        val drinksTaken = appDataRepository.getDrinks()
        return drinksTaken.map {
            it.map { drink ->
                LocationUIModel(
                    drink.dateTaken.toString(),
                    drink.location.long,
                    drink.location.lat,
                    drinkTypeRepository.provideDrinks()[drink.type.ordinal]
                )
            }
        }
    }
}
