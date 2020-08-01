package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import androidx.lifecycle.LiveData

data class User(
    val age: Int,
    val weight: Measure,
    val gender: Gender,
    val drinks: LiveData<List<Drink>>
)

