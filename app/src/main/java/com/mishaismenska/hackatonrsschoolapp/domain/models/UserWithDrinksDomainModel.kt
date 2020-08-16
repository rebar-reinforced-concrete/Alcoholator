package com.mishaismenska.hackatonrsschoolapp.domain.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender

data class UserWithDrinksDomainModel(
    val drinks: List<DrinkDomainModel>,
    val age: Int,
    val weight: Measure,
    val gender: Gender
)
