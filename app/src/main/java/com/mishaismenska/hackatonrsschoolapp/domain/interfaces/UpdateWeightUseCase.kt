package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface UpdateWeightUseCase {
    suspend fun updateWeight(weight: Int)
}
