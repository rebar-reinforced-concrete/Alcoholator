package com.mishaismenska.hackatonrsschoolapp.data.database.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserWithDrinksDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserWithDrinksDao {
    @Query("SELECT * FROM USER")
    fun getUser(): Flow<List<UserDataModel>>

    @Query("SELECT * FROM DRINKS ORDER BY dateTaken DESC")
    fun getDrinks(): Flow<List<DrinkDataModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrink(drink: DrinkDataModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserDataModel)

    @Delete
    suspend fun deleteDrink(drink: DrinkDataModel)

    @Query("DELETE FROM USER")
    suspend fun resetUser()

    @Query("DELETE FROM DRINKS")
    suspend fun resetDrinks()

    @Query("UPDATE USER SET weightValueInKg = :newValue")
    suspend fun setWeight(newValue: Double)

    @Query("UPDATE USER SET userName = :newName")
    suspend fun setName(newName: String)

    @Query("UPDATE USER SET genderId = :newValue")
    suspend fun setGender(newValue: Int)

    @Query("UPDATE USER SET drinksAlteredTimestamp = :newValue")
    suspend fun setDrinksAlteredTimestamp(newValue: Long)

    @Transaction
    @Query("SELECT * FROM User")
    fun getUserWithDrinks(): UserWithDrinksDataModel?
}
