package com.mishaismenska.hackatonrsschoolapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user")
data class UserDataModel(
    @PrimaryKey(autoGenerate = true) val userId: Long,
    val createdOn: LocalDate,
    val ageOnCreation: Int,
    val genderId: Int,
    val weightValueInKg: Double,
    val userName: String
)
