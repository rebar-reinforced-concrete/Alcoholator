package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import java.time.Duration
import java.time.LocalDateTime

data class UserStateDataModel(
    val alcoholConcentration: Double,
    val soberTime: Duration,
    val lastUpdateTime: LocalDateTime = LocalDateTime.now()
)
