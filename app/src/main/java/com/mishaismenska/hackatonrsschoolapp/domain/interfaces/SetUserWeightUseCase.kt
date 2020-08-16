package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface SetUserWeightUseCase {
    suspend fun setUserWeight(weight: Int)
}
