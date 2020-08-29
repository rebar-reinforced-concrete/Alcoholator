package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.IsImperialMeasureSystemUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val isImperialMeasureSystemUseCase: IsImperialMeasureSystemUseCase,
    private val context: Context
) : ViewModel() {

    private val _isFragmentOpened = MutableLiveData(true)
    val isFragmentOpened: LiveData<Boolean>
        get() = _isFragmentOpened

    fun addUser(age: Int, weight: Double, gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.addUser(UserSubmissionUIModel(age, weight, gender))
            _isFragmentOpened.postValue(false)
        }
    }

    fun getWeightInputHint() = if (isImperialMeasureSystemUseCase.checkIfMeasureSystemImperial()) {
        context.resources.getString(R.string.weight_pounds)
    } else {
        context.resources.getString(R.string.weight_kg)
    }

    override fun onCleared() {
        _isFragmentOpened.postValue(true)
    }
}
