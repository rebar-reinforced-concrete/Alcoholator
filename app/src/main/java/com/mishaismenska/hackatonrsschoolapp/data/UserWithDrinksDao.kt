package com.mishaismenska.hackatonrsschoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mishaismenska.hackatonrsschoolapp.data.entities.DrinkEntity
import com.mishaismenska.hackatonrsschoolapp.data.entities.UserEntity

@Dao
interface UserWithDrinksDao {
    @Query("SELECT * FROM USER")
    fun getUser(): List<UserEntity>

    @Query("SELECT * FROM DRINKS")
    fun getDrinks(): LiveData<List<DrinkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrink(drink: DrinkEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)

    @Query("DELETE FROM USER")
    suspend fun resetUser()

    @Query("DELETE FROM DRINKS")
    suspend fun resetDrinks()

    @Query("UPDATE USER SET weight = :newValue")
    suspend fun updateWeight(newValue: Int)
}
