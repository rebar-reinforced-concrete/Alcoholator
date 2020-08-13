package com.mishaismenska.hackatonrsschoolapp.data.staticPresets

enum class Gender(val isMale: Boolean) {
    MALE(true),
    FEMALE(false),
    MALE_IDENTIFIES_AS_FEMALE(true),
    FEMALE_IDENTIFIES_AS_MALE(false),
    UNCERTAIN(false)
}
