package com.mishaismenska.hackatonrsschoolapp.interfaces

import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding

interface UserInputValidatingManager {
    fun validate(binding: FragmentAddUserBinding) : Boolean
}
