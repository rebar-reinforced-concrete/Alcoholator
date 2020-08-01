package com.mishaismenska.hackatonrsschoolapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.UserInputValidatingManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(private val userInputValidatingManager: UserInputValidatingManager) : ViewModel() {

    fun validate(binding: FragmentAddUserBinding): Boolean{
        return userInputValidatingManager.validate(binding)
    }

    fun addUser(binding: FragmentAddUserBinding){
        val age = binding.ageInput.text.toString().toInt()
        val weight = binding.weightInput.text.toString().toInt()
        val gender = when(binding.genderInput.text.toString()){
            binding.genderInput.adapter.getItem(0) -> Gender.MALE
            binding.genderInput.adapter.getItem(1) -> Gender.FEMALE
            binding.genderInput.adapter.getItem(2) -> Gender.MALE_IDENTIFIES_AS_FEMALE
            binding.genderInput.adapter.getItem(3) -> Gender.FEMALE_IDENTIFIES_AS_MALE
            else -> Gender.UNCERTAIN
        }
    }
}
