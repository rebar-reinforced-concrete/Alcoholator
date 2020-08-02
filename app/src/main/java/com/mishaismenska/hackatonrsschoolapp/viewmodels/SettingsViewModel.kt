package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
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

class SettingsViewModel @Inject constructor(private val repository: AppDataRepository): ViewModel() {

    fun resetDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reset()
        }
    }

    var units = false

    fun setWeight(pref: EditTextPreference?) {
        val formatter = MeasureFormat.getInstance(ULocale.ENGLISH, MeasureFormat.FormatWidth.NUMERIC)
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserWithDrinks()
            units = PreferenceManager.getDefaultSharedPreferences(pref?.context).getBoolean("units", false)
            if(units)
                pref?.text = formatter.format(Measure(kgToLb(user.weight.number as Int), MeasureUnit.POUND))

            else
                pref?.text = formatter.format(user.weight)

        }
    }

    fun updateWeight(newValue: String): Boolean {
        var input = newValue.toInt()
        if(units)
            input = lbToKg(input).toInt()
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeight(input)
        }
        return true
    }
}