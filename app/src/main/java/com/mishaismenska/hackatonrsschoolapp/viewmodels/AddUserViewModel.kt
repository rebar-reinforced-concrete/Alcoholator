package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.ui.DbResultsListener
import com.mishaismenska.hackatonrsschoolapp.ui.USER_CREATED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val userInputValidatingManager: UserInputValidatingManager,
    private val repository: AppDataRepository,
    private val context: Context
) : ViewModel() {

    fun validate(binding: FragmentAddUserBinding): Boolean {
        return userInputValidatingManager.validate(binding)
    }

    fun addUser(binding: FragmentAddUserBinding, listener: DbResultsListener) {
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
            repository.addUser(age, Measure(weight, MeasureUnit.KILOGRAM), gender)
            PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(USER_CREATED, true)
                .apply()
            Handler(context.mainLooper).post {
                listener.onUserAdded()
            }
        }
    }
}
