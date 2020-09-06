package com.mishaismenska.hackatonrsschoolapp.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import javax.inject.Inject

class PermissionManagerImpl @Inject constructor(private val context: Context) : PermissionManager {
    override fun checkAndRequestGPSLocationPermissions(fragment: Fragment) {
        checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            FINE_LOCATION_CODE,
            fragment
        )
    }

    private fun checkPermission(permission: String, requestCode: Int, fragment: Fragment) {
        if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
            fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    private companion object {
        const val FINE_LOCATION_CODE = 101
    }
}
