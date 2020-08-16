package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val userInputValidatingManager: UserInputValidatingManager,
    private val addUserUseCase: AddUserUseCase,
    private val context: Context
) : ViewModel() {
    val isFragmentOpened = MutableLiveData(true)

    fun validate(binding: FragmentAddUserBinding): Boolean {
        return userInputValidatingManager.validateUserInput(binding)
    }

    fun addUser(binding: FragmentAddUserBinding) {
        val age = binding.ageInput.text.toString().toInt()
        val weight = binding.weightInput.text.toString().toInt()
        val gender = binding.genderInput.text.toString()

        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.addUser(
                UserSubmissionUIModel(
                    age,
                    Measure(weight, MeasureUnit.KILOGRAM),
                    gender
                )
            )
            isFragmentOpened.postValue(false)
        }
    }

    override fun onCleared() {
        isFragmentOpened.postValue(true)
    }
}
