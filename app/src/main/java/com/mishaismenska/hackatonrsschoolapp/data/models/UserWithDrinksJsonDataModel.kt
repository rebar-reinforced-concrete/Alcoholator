package com.mishaismenska.hackatonrsschoolapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserWithDrinksJsonDataModel(
    val addedTimestamp: Long,
    val userAlteredTimestamp: Long,
    val genderId: Int,
    val weightInKg: Double,
    val userName: String,
    val drinks: List<DrinkJsonDataModel>,
    val drinksAlteredTimeStamp: Long,
    val userAgeOnCreation: Int
)
