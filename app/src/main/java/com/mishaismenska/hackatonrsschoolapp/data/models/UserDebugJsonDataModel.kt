package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDebugJsonDataModel(
    val id: String,
    val addedTimestamp: Long,
    val genderId: Int,
    val weightInKg: Double,
    val userName: String
)
