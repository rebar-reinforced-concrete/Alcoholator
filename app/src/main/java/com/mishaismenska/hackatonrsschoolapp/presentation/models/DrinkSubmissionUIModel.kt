package com.mishaismenska.hackatonrsschoolapp.presentation.models

import android.icu.util.Measure
import java.time.LocalDateTime

data class DrinkSubmissionUIModel(
    val type: String,
    val dateTaken: LocalDateTime,
    val volume: Measure,
    val eaten: Boolean,
    val location: LocationUIModel
)
