package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinkAddDrinkJsonDataModel(
    val GoogleId: String,
    val DrinkType: Int,
    val DateTimeTaken: Long,
    val VolumeInMl: Int,
    val Eaten: Boolean,
    val long: Double,
    val lat: Double
)
