package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.kgToLb
import com.mishaismenska.hackatonrsschoolapp.data.lbToKg
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ResetDataBaseUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateUserGenderUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateUserNameUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UpdateWeightUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants.minimalWeightDifferenceMargin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class SettingsViewModel @Inject constructor(
    private val getUserUseCase: GetUserForSettingsUseCase,
    private val resetDataBaseUseCase: ResetDataBaseUseCase,
    private val updateWeightUseCase: UpdateWeightUseCase,
    private val updateNameUseCase: UpdateUserNameUseCase,
    private val updateUserGenderUseCase: UpdateUserGenderUseCase,
    private val context: Context
) : ViewModel() {

    private val formatter =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
    val userLiveData: LiveData<UserSettingsUIModel> = liveData {
        emit(getUserUseCase.getUser())
    }


    fun loadName(namePreference: EditTextPreference) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            context.getString(
                R.string.name_key
            ), userLiveData.value!!.userName
        ).apply()
        namePreference.text = userLiveData.value!!.userName
    }

    fun loadWeight(
        isImperial: Boolean,
        weightPreference: EditTextPreference
    ) {
        val unitWeight = if (isImperial)
            formatter.format(
                Measure(
                    kgToLb(userLiveData.value!!.weight.number.toInt()),
                    MeasureUnit.POUND
                )
            ) else formatter.format(userLiveData.value!!.weight)
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            context.getString(
                R.string.weight_key
            ), unitWeight
        ).apply()
        weightPreference.text = unitWeight
    }

    fun loadGender(genderPreference: ListPreference) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            context.getString(R.string.gender_key), userLiveData.value!!.gender.toString()
        ).apply()
        genderPreference.value = userLiveData.value!!.gender.toString()
    }

    fun resetDB() { //TODO: discuss the mechanic, that throws the user out to the registration screen, idk where to put it)))
        viewModelScope.launch(Dispatchers.IO) {
            resetDataBaseUseCase.resetDataBase()
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
        }
    }

    private fun cleanInputValue(text: String) =
        text.split(".")[0].filter { it.isDigit() }.toInt()

    fun updateWeight(newValue: String?, isImperial: Boolean) {
        val cleanInput = cleanInputValue(newValue!!)
        if (!closeEnough(userLiveData.value!!.weight, cleanInput))
            viewModelScope.launch(Dispatchers.IO) {
                val unitWeight = if (isImperial) lbToKg(cleanInput)
                else cleanInput
                updateWeightUseCase.updateWeight(unitWeight)
            }
    }

    private fun closeEnough(weight: Measure, newWeight: Int): Boolean {
        return abs(kgToLb(weight.number.toInt()) - newWeight) <= minimalWeightDifferenceMargin
    }

    fun updateName(newValue: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNameUseCase.updateUserName(newValue!!)
        }
    }

    fun updateGender(genderId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserGenderUseCase.updateGender(genderId!!.toInt())
        }
    }

}
