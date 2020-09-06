package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import android.content.Context
import androidx.fragment.app.Fragment
import com.mishaismenska.hackatonrsschoolapp.presentation.AddDrinkFragment

interface AlertDialogManager {
    fun showEditNameAlertDialog(existingValue: String, context: Context, updater: (newValue: String) -> Unit)
    fun showEditWeightAlertDialog(existingValue: String, context: Context, updater: (newValue: String) -> Unit)
    fun showEditGenderAlertDialog(existingValue: Int, context: Context, updater: (newValue: Int) -> Unit)
    fun showEnableGPSServicesDialog(context: Context)
    fun showPermissionsExplanation(context: Context, event: (fragment: Fragment) -> Unit, fragment: AddDrinkFragment)
}
