package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinkJsonDataModel(
    val typeId: Int,
    val dateTimeTaken: Long,
    val volumeInMl: Int,
    val eaten: Boolean
)
