package com.mishaismenska.hackatonrsschoolapp.presentation.models

import android.icu.util.Measure

data class UserSettingsUIModel(
    val weight: Measure,
    val userName: String = "",
    val genderId: Int
)
