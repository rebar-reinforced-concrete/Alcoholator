package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import androidx.lifecycle.LiveData
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Gender

data class User(
    val age: Int,
    val weight: Measure,
    val gender: Gender,
    val drinks: LiveData<List<Drink>>
)

