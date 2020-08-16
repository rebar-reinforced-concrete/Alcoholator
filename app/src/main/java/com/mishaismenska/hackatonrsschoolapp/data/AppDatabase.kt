package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserWithDrinksDao
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants.DBNAME

@Database(entities = [UserDataModel::class, DrinkDataModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): UserWithDrinksDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DBNAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
