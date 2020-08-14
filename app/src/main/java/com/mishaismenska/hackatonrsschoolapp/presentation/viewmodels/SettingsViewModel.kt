package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.data.kgToLb
import com.mishaismenska.hackatonrsschoolapp.data.lbToKg
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ResetDataBaseUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateWeightUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getUserUseCase: GetUserForSettingsUseCase,
    private val resetDataBaseUseCase: ResetDataBaseUseCase,
    private val updateWeightUseCase: UpdateWeightUseCase,
    private val context: Context
) : ViewModel() {

    private val format =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)

    fun resetDB() {
        viewModelScope.launch(Dispatchers.IO) {
            resetDataBaseUseCase.resetDataBase()
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
        }
    }

    fun setWeightPreference(units: Boolean, value: Measure? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val userWeight = value ?: getUserUseCase.getUser().weight
            val unitWeight = if (units) {
                format.format(
                    Measure(kgToLb(userWeight.number as Int), MeasureUnit.POUND)
                )
            } else {
                format.format(userWeight)
            }

            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString("weight", unitWeight)
                .apply()
        }
    }


    fun updateWeight(newValue: String?, units: Boolean): Boolean {
        val cleanInput = newValue!!.split(".")[0].filter { it.isDigit() }.toInt()

        Log.d("update weight", cleanInput.toString())
        if (units) {
            val kgValue = lbToKg(cleanInput)
            Log.d("update weight kg", kgValue.toString())
            viewModelScope.launch(Dispatchers.IO) {
                updateWeightUseCase.updateWeight(kgValue)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                updateWeightUseCase.updateWeight(cleanInput)
            }
        }
        return true
    }
}
