package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserWithDrinksDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    suspend fun getUserWithDrinks(): UserWithDrinksDataModel
    suspend fun getUser(): Flow<List<UserDataModel>>
    suspend fun getDrinks(): Flow<List<DrinkDataModel>>
    suspend fun addDrink(drinkDomainModel: DrinkDomainModel)
    suspend fun deleteDrink(recyclerPosition: Int)
    suspend fun addUser(age: Int, weight: Measure, gender: Gender)
    suspend fun setUserName(newName: String)
    suspend fun reset()
    suspend fun setWeight(newValue: Double)
    suspend fun setGender(newValue: Int)
}
