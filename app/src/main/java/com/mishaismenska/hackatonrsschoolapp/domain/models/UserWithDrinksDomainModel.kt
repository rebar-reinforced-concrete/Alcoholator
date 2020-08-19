package com.mishaismenska.hackatonrsschoolapp.domain.models

import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender

data class UserWithDrinksDomainModel(
    val drinks: List<DrinkDomainModel>,
    val age: Int,
    val weight: Double,
    val gender: Gender
)
