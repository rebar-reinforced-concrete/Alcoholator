package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import android.content.Context

interface AlertDialogManager {
    fun showEditNameAlertDialog(existingValue: String, context: Context, updater: (newValue: String) -> Unit)
    fun showEditWeightAlertDialog(existingValue: String, context: Context, updater: (newValue: String) -> Unit)
}
