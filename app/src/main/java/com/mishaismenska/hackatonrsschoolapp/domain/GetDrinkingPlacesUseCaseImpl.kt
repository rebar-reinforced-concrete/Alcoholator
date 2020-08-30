package com.mishaismenska.hackatonrsschoolapp.domain

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinkingPlacesUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDrinkingPlacesUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val drinkTypeRepository: DrinkTypeRepository
) :
    GetDrinkingPlacesUseCase {
    override suspend fun getPlaces(): Flow<List<LocationUIModel?>> {
        val drinksTaken = appDataRepository.getDrinks()
        return drinksTaken.map {
            it.map { drink ->
                if (drink.location != null)
                    LocationUIModel(
                        drink.dateTaken.toString(), // make it produce normal time
                        drink.location.long,
                        drink.location.lat,
                        drinkTypeRepository.provideDrinks()[drink.type.ordinal]
                    )
                else null
            }
        }
    }
}
