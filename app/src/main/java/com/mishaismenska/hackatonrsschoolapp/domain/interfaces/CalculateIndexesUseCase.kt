package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface CalculateIndexesUseCase {
    fun calculateIndexes(drinkTypeStringRepresentation: String): List<Int>
}
