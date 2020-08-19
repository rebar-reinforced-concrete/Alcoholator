package com.mishaismenska.hackatonrsschoolapp.presentation.models

data class UserSettingsUIModel(
    val weight: Double,
    val userName: String = "",
    val genderId: Int
)
