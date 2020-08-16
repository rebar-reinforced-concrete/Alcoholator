package com.mishaismenska.hackatonrsschoolapp.presentation.models

import android.icu.util.Measure

data class UserSubmissionUIModel(
    val age: Int,
    val weight: Measure,
    val gender: String
)
