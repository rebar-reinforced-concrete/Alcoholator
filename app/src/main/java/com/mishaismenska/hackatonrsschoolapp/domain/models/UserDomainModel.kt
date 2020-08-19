package com.mishaismenska.hackatonrsschoolapp.domain.models

import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender

data class UserDomainModel(
    val age: Int,
    val weight: Double,
    val gender: Gender,
    val name: String
)
