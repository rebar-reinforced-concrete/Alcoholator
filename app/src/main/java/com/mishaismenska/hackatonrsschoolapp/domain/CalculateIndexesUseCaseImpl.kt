package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculateIndexesUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import javax.inject.Inject

class CalculateIndexesUseCaseImpl @Inject constructor(private val drinkTypeRepository: DrinkTypeRepository) :
    CalculateIndexesUseCase {
    override fun calculateIndexes(drinkTypeStringRepresentation: String): List<Int> {
        val typeIndex = drinkTypeRepository.provideDrinks().indexOf(drinkTypeStringRepresentation)
        return DrinkPreset.values()[typeIndex].typicalTares.map { it.ordinal }
    }
}
