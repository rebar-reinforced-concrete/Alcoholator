package com.mishaismenska.hackatonrsschoolapp.logic

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.UserInputValidatingManager
import javax.inject.Inject

class UserInputValidatingManagerImpl @Inject constructor(private val context: Context) : UserInputValidatingManager{
    private fun isEmpty(wrapper: TextInputLayout): Boolean{
        val text = wrapper.editText!!.text.toString()
        return if(text.trim().isEmpty()){
            wrapper.error = context.getString(R.string.couldnt_be_empty)
            true
        } else{
            wrapper.error = null
            false
        }
    }

    override fun validate(binding: FragmentAddUserBinding): Boolean {
        val isAgeCorrect = !isEmpty(binding.ageInputWrapper)
        val isWeightCorrect = !isEmpty(binding.weightInputWrapper)
        val isGenderCorrect = !isEmpty(binding.genderInputWrapper)
        return isAgeCorrect && isWeightCorrect && isGenderCorrect
    }
}