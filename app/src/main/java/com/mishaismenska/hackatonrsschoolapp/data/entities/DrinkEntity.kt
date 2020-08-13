package com.mishaismenska.hackatonrsschoolapp.data.entities

import android.icu.util.MeasureUnit
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "drinks")
data class DrinkEntity(
    @PrimaryKey(autoGenerate = true) var drinkId: Long,
    val consumedBy: Long,
    val type: Int,
    val dateTaken: LocalDateTime,
    val volume: Int,
    val unit: MeasureUnit,
    val eaten: Boolean
)
