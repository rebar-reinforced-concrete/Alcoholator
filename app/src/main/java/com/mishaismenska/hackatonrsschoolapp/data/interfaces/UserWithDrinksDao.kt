package com.mishaismenska.hackatonrsschoolapp.data.interfaces

import androidx.room.*
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

    @Query("UPDATE USER SET weight = :newValue")
    suspend fun updateWeight(newValue: Int)

    @Query("UPDATE USER SET userName = :newName")
    suspend fun updateName(newName: String)

    @Query("UPDATE USER SET gender = :newValue")
    suspend fun updateGender(newValue: Int)

    @Transaction
    @Query("SELECT * FROM User")
    fun getUserWithDrinks(): UserWithDrinksDataModel
}
