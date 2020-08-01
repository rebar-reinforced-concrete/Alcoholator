package com.mishaismenska.hackatonrsschoolapp.data

import androidx.room.*
import com.mishaismenska.hackatonrsschoolapp.data.entities.DrinkEntity
import com.mishaismenska.hackatonrsschoolapp.data.entities.UserEntity
import com.mishaismenska.hackatonrsschoolapp.data.entities.UserWithDrinks

@Dao
interface UserWithDrinksDao {
    @Transaction
    @Query("SELECT * FROM USER")
    fun getUserWithDrinksEntity(): UserWithDrinks

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrink(drink: DrinkEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}