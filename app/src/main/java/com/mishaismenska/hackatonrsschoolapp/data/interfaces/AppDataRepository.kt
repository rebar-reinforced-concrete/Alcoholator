package com.mishaismenska.hackatonrsschoolapp.data.interfaces

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserWithDrinksDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    suspend fun getUser(): Flow<List<UserDataModel>>
    suspend fun addDrink(drinkDomainModel: DrinkDomainModel)
    suspend fun deleteDrink(recyclerPosition: Int)
    suspend fun getDrinks(): Flow<List<DrinkDataModel>>
    suspend fun addUser(age: Int, weight: Measure, gender: Gender)
    suspend fun setWeight(newValue: Int)
    suspend fun setUserName(newName: String)
    suspend fun setGender(newValue: Int)
    suspend fun getUserWithDrinks(): UserWithDrinksDataModel
    suspend fun reset()
}
