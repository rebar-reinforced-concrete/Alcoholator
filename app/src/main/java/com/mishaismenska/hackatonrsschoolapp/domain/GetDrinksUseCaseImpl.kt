package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDrinksUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : GetDrinksUseCase {
    override suspend fun getDrinks(): Flow<List<DrinkUIModel>> = appDataRepository.getDrinks().map {
        it.map { drink ->
            DrinkUIModel(DrinkPreset.values()[drink.typeId], drink.dateTaken, Measure(drink.volumeValueInMl, drink.unit))
        }
    }
}
