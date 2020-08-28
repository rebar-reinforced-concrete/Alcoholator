package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserJsonDataModel(
    val CreationTimeStamp: Long,
    val GenderId: Int,
    val WeightInKg: Double,
    val GoogleUserIdToken: String,
    val Age: Int
)
