package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel @Inject constructor(
    private val getUserUseCase: GetUserForSettingsUseCase,
    private val resetDataBaseUseCase: ResetDataBaseUseCase,
    private val setUserWeightUseCase: SetUserWeightUseCase,
    private val setNameUseCase: SetUserNameUseCase,
    private val updateUserGenderUseCase: SetUserGenderUseCase,
    private val ifUserWeightValidUseCase: CheckIfUserWeightValidUseCase,
    private val measureSystemsManager: MeasureSystemsManager,
    private val getGendersUseCase: GetGendersUseCase
) : ViewModel() {

    private val formatter = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)

    private val _userLiveData = MutableLiveData<UserSettingsUIModel>()
    val userLiveData: LiveData<UserSettingsUIModel>
        get() = _userLiveData

    private val _showWrongWeightSnackbar = MutableLiveData<Boolean>(false)
    val showWrongWeightSnackbar: LiveData<Boolean>
        get() = _showWrongWeightSnackbar

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase.getUser().collect {
                if (it != null) {
                    _userLiveData.postValue(it)
                }
            }
        }
    }

    fun getGenders() = getGendersUseCase.getGenders()

    fun resetDB() {
        viewModelScope.launch(Dispatchers.IO) {
            resetDataBaseUseCase.resetDataBase()
        }
    }

    fun getFormattedWeight(weight: Double): String {
        val measureUnit = if (measureSystemsManager.checkIfMeasureSystemImperial()) MeasureUnit.POUND else MeasureUnit.KILOGRAM
        return formatter.formatMeasures(Measure(weight, measureUnit))
    }

    fun updateWeight(newValue: String) {
        val weight = newValue.toDouble()
        if (ifUserWeightValidUseCase.checkIfUserWeightValid(weight)) {
            viewModelScope.launch(Dispatchers.IO) {
                setUserWeightUseCase.setUserWeight(weight)
            }
        } else {
            _showWrongWeightSnackbar.postValue(true)
        }
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
}
