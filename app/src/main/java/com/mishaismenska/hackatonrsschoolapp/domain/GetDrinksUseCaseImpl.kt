package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDrinksUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val measureSystemsManager: MeasureSystemsManager) : GetDrinksUseCase {
    override suspend fun getDrinks(): Flow<List<DrinkUIModel>> = appDataRepository.getDrinks().map {
        it.map { drink ->
            DrinkUIModel(drink.type, drink.dateTaken, measureSystemsManager.convertVolumeToImperialIfRequired(drink.volume.number.toInt()))
        }
    }
}
