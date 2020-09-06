package com.mishaismenska.hackatonrsschoolapp.domain

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetCurrentLocationUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import java.time.LocalDateTime
import javax.inject.Inject

class GetCurrentLocationUseCaseImpl @Inject constructor() :
    GetCurrentLocationUseCase, LocationListener {
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(locationManager: LocationManager): LocationUIModel {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this)
        val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return LocationUIModel(
            LocalDateTime.now().toString(),
            locationGPS!!.longitude,
            locationGPS.latitude
        )
    }

    override fun onLocationChanged(p0: Location) {
        Log.d("LOCATION", p0.toString())
    }
}
