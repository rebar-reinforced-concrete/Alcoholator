package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAlterNameJsonDataModel(
    val GoogleUserIdToken: String,
    val UserName: String
)
