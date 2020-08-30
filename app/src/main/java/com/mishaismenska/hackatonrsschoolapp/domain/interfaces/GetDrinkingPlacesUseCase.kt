package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import kotlinx.coroutines.flow.Flow

interface GetDrinkingPlacesUseCase {
    suspend fun getPlaces(): Flow<List<LocationUIModel?>>
}
