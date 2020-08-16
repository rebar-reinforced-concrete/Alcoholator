package com.mishaismenska.hackatonrsschoolapp.data

import android.icu.util.MeasureUnit
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toDateTime(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateTimeString(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toUnitString(unit: MeasureUnit?): String? {
        return unit?.toString()
    }

    @TypeConverter
    fun toUnit(unitString: String?): MeasureUnit? {
        return if (unitString == null) {
            null
        } else {
            MeasureUnit.getAvailable().find { it.toString() == unitString } // FIXME
        }
    }
}
