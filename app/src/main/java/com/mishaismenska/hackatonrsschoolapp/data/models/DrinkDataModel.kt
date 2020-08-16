package com.mishaismenska.hackatonrsschoolapp.data.models

import android.icu.util.MeasureUnit
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "drinks")
data class DrinkDataModel(
    @PrimaryKey(autoGenerate = true) var drinkId: Long,
    val consumedBy: Long,
    val typeId: Int,
    val dateTaken: LocalDateTime,
    val volumeValueInMl: Int,
    val unit: MeasureUnit,
    val eaten: Boolean
)
