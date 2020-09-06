package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import android.location.LocationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel

interface GetCurrentLocationUseCase {
    fun getCurrentLocation(locationManager: LocationManager): LocationUIModel
}
