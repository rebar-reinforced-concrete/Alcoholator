package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding

interface UserInputValidatingManager {
    fun validateUserInput(binding: FragmentAddUserBinding): Boolean
}
