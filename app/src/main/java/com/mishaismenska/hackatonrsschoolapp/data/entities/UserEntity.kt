package com.mishaismenska.hackatonrsschoolapp.data.entities

import android.icu.util.MeasureUnit
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val userId: Long,
    val createdOn: LocalDate,
    val ageOnCreation: Int,
    val gender: Int,
    val weight: Int,
    val unit: MeasureUnit
)