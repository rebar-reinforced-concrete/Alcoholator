package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinkDebugJsonDataModel(
    val id: String,
    val consumedBy: String,
    val dateTimeTaken: Long,
    val typeId: Int,
    val volumeInMl: Int,
    val eaten: Boolean
)
