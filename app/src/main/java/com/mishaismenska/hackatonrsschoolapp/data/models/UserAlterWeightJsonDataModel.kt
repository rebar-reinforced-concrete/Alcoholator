package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAlterWeightJsonDataModel(
    val GoogleUserIdToken: String,
    val UserWeight: Double
)
