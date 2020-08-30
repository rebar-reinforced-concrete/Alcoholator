package com.mishaismenska.hackatonrsschoolapp.domain

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetCurrentLocationUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import java.time.LocalDateTime
import javax.inject.Inject

class GetCurrentLocationUseCaseImpl @Inject constructor() :
    GetCurrentLocationUseCase {
    @SuppressLint("MissingPermission") // we are requesting permissions
    override fun getCurrentLocation(locationManager: LocationManager): LocationUIModel {
        val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return LocationUIModel(
            LocalDateTime.now().toString(),
            locationGPS!!.longitude,
            locationGPS.latitude
        )
    }
}
