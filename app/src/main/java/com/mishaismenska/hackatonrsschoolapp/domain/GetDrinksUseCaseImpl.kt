package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import androidx.lifecycle.LiveData
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPresets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDrinksUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : GetDrinksUseCase {
    override suspend fun getDrinks(): Flow<List<DrinkUIModel>> = appDataRepository.getDrinks().map {
        it.map {drink ->
            DrinkUIModel(DrinkPresets.values()[drink.type], drink.dateTaken, Measure(drink.volume, drink.unit))
        }
    }
}
