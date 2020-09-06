package com.mishaismenska.hackatonrsschoolapp.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetGendersUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AlertDialogManager
import javax.inject.Inject

class AlertDialogManagerImpl @Inject constructor(private val getGendersUseCase: GetGendersUseCase) :
    AlertDialogManager {

    private var currentGendersItem = 0

    override fun showEditNameAlertDialog(
        existingValue: String,
        context: Context,
        updater: (newValue: String) -> Unit
    ) {
        showEditPreferenceDialog(
            context.getString(R.string.name_edit),
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
            existingValue,
            InputType.TYPE_NUMBER_FLAG_DECIMAL.or(InputType.TYPE_CLASS_NUMBER),
            updater,
            context
        )
    }

    override fun showEditGenderAlertDialog(existingValue: Int, context: Context, updater: (newValue: Int) -> Unit) {
        currentGendersItem = existingValue
        AlertDialog.Builder(context).setTitle(context.getString(R.string.your_gender))
            .setSingleChoiceItems(getGendersUseCase.getGenders(), existingValue) { dialog, which ->
                currentGendersItem = which
            }
            .setPositiveButton("OK") { dialog, which ->
                updater.invoke(currentGendersItem)
            }
            .create()
            .show()
    }

    private fun showEditPreferenceDialog(
        title: String,
        defaultValue: String,
        inputType: Int,
        updater: (newValue: String) -> Unit,
        context: Context
    ) {
        val margin = (context.resources.displayMetrics.density * 18).toInt()
        val layout = LinearLayout(context).apply { setPadding(margin, margin / 2, margin, 0) }
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val editText = EditText(context)
        editText.setText(defaultValue)
        editText.inputType = inputType
        layout.addView(editText, layoutParams)
        val dialog: AlertDialog? = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(layout)
            .setPositiveButton(
                context.getString(R.string.ok)
            ) { dialog, which ->
                updater(editText.text.toString())
            }
            .create()
        dialog!!.show()
    }

    override fun showEnableGPSServicesDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.enable_gps)).setCancelable(false).setPositiveButton(
            context.getString(R.string.yeah)
        ) { _, _ -> context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                context.getString(R.string.nah)
            ) { dialog, _ -> dialog.cancel() }
        val alertDialog: AlertDialog? = builder.create()
        alertDialog!!.show()
    }

    override fun showPermissionsExplanation(context: Context, event: (fragment: Fragment) -> Unit, fragment: AddDrinkFragment) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(
            context.getString(R.string.gps_explanation))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.yeah)) { dialog, _ ->
                dialog.dismiss()
                event(fragment)
            }
        val alertDialog: AlertDialog? = builder.create()
        alertDialog!!.show() }
}
