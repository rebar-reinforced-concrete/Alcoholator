package com.mishaismenska.hackatonrsschoolapp.data

import androidx.fragment.app.Fragment

interface PermissionManager {
    fun checkAndRequestGPSLocationPermissions(fragment: Fragment)
}
