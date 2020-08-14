package com.mishaismenska.hackatonrsschoolapp.presentation.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender

data class UserSubmissionUIModel(
    val age: Int,
    val weight: Measure,
    val gender: Gender
)
