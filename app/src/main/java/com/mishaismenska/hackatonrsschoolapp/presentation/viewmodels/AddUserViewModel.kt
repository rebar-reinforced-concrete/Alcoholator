package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.DbResultsListener
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.USER_CREATED
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val userInputValidatingManager: UserInputValidatingManager,
    private val addUserUseCase: AddUserUseCase,
    private val context: Context
) : ViewModel() {

    fun validate(binding: FragmentAddUserBinding): Boolean {
        return userInputValidatingManager.validate(binding)
    }

    fun addUser(binding: FragmentAddUserBinding, dbResultsListener: DbResultsListener) {
        val age = binding.ageInput.text.toString().toInt()
        val weight = binding.weightInput.text.toString().toInt()
        val gender = when (binding.genderInput.text.toString()) {
            binding.genderInput.adapter.getItem(0) -> Gender.MALE
            binding.genderInput.adapter.getItem(1) -> Gender.FEMALE
            binding.genderInput.adapter.getItem(2) -> Gender.MALE_IDENTIFIES_AS_FEMALE
            binding.genderInput.adapter.getItem(3) -> Gender.FEMALE_IDENTIFIES_AS_MALE
            else -> Gender.UNCERTAIN
        }
        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.addUser(UserSubmissionUIModel(age, Measure(weight, MeasureUnit.KILOGRAM), gender))
            Handler(context.mainLooper).post {
                dbResultsListener.onUserAdded()
            }
            PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(USER_CREATED, true)
                .apply()
        }
    }
}
