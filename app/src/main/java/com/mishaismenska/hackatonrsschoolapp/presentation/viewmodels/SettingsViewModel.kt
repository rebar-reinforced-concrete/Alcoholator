package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.function.DoubleUnaryOperator
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getUserUseCase: GetUserForSettingsUseCase,
    private val resetDataBaseUseCase: ResetDataBaseUseCase,
    private val setUserWeightUseCase: SetUserWeightUseCase,
    private val setNameUseCase: SetUserNameUseCase,
    private val updateUserGenderUseCase: SetUserGenderUseCase,
    private val convertIfRequiredUseCase: ConvertIfRequiredUseCase,
    private val context: Context
) : ViewModel() {

    private val formatter =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
    val userLiveData: LiveData<UserSettingsUIModel> = liveData {
        emit(getUserUseCase.getUser())
    }

    fun loadName(currentUserName: TextView) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            context.getString(
                R.string.name_key
            ), userLiveData.value!!.userName
        ).apply()
        currentUserName.text = userLiveData.value!!.userName
    }

    fun loadWeight(currentWeight: TextView) {
        val unitWeight =
            convertIfRequiredUseCase.convertToImperialIfRequired(userLiveData.value!!.weight)
        val formattedWeight = formatter.format(unitWeight)
        currentWeight.text = formattedWeight
    }

    fun loadGender(genderPreference: Spinner) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
            context.getString(R.string.gender_key), userLiveData.value!!.genderId.toString()
        ).apply()
        genderPreference.setSelection(userLiveData.value!!.genderId)
    }

    val genderChangedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
            updateGender(position)
        }

    }

    fun resetDB() {
        viewModelScope.launch(Dispatchers.IO) {
            resetDataBaseUseCase.resetDataBase()
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
        }
    }

    fun updateWeight(newValue: String): String? {
        val cleanInput = newValue.toDouble()
        val unitWeight = convertIfRequiredUseCase.convertWeightToMetricIfRequired(cleanInput)
        viewModelScope.launch(Dispatchers.IO) {
            setUserWeightUseCase.setUserWeight(unitWeight)
        }
        return formatter.format(convertIfRequiredUseCase.convertToImperialIfRequired(Measure(
            unitWeight,
            MeasureUnit.KILOGRAM
        )))
    }

    fun updateName(newValue: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            setNameUseCase.updateUserName(newValue!!)
        }
    }

    fun updateGender(genderId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserGenderUseCase.setUserGender(genderId)
        }
    }

    fun getUserName(): String = userLiveData.value!!.userName
    fun getUserWeight(): String = formatter.format(userLiveData.value!!.weight)
}