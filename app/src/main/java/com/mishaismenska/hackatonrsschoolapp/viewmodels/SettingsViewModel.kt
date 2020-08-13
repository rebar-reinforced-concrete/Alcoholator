package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.data.kgToLb
import com.mishaismenska.hackatonrsschoolapp.data.lbToKg
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val repository: AppDataRepository,
    val context: Context
) : ViewModel() {

    private val format =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)

    fun resetDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reset()
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
        }
    }

    fun setWeightPreference(units: Boolean, value: Measure? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val userWeight = value ?: repository.getUserWithDrinks().weight
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
                repository.updateWeight(kgValue)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateWeight(cleanInput)
            }
        }
        return true
    }
}