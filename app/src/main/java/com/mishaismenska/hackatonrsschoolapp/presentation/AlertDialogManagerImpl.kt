package com.mishaismenska.hackatonrsschoolapp.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AlertDialogManager
import javax.inject.Inject

class AlertDialogManagerImpl @Inject constructor():
    AlertDialogManager {

    override fun showEditNameAlertDialog(
        existingValue: String,
        context: Context,
        updater: (newValue: String) -> Unit
    ) {
        showEditPreferenceDialog(
            context.getString(R.string.name_edit),
            context.getString(R.string.name_edit_message),
            existingValue,
            InputType.TYPE_CLASS_TEXT,
            updater,
            context
        )
    }

    override fun showEditWeightAlertDialog(
        existingValue: String,
        context: Context,
        updater: (newValue: String) -> Unit
    ) {
        showEditPreferenceDialog(
            context.getString(R.string.weight_edit),
            context.getString(R.string.weight_edit_message),
            existingValue.split(" ")[0].filter{it.isDigit()},
            InputType.TYPE_CLASS_NUMBER,
            updater,
            context
        )
    }

    private fun showEditPreferenceDialog(
        title: String,
        message: String,
        defaultValue: String,
        inputType: Int,
        updater: (newValue: String) -> Unit,
        context: Context
    ) {
        val editText = EditText(context)
        editText.setText(defaultValue)
        editText.inputType = inputType
        val dialog: AlertDialog? = android.app.AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setView(editText)
            .setPositiveButton(context.getString(R.string.ok),
                DialogInterface.OnClickListener { dialog, which ->
                    updater(editText.text.toString())
                })
            .create()
        dialog!!.show()
    }
}