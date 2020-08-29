package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    suspend fun getUserWithDrinks(): UserWithDrinksDomainModel?
    suspend fun getUser(): Flow<List<UserDomainModel>>
    suspend fun getDrinks(): Flow<List<DrinkDomainModel>>
    fun addDrink(drinkDomainModel: DrinkDomainModel, addToServer: Boolean)
    suspend fun deleteDrink(recyclerPosition: Int)
    suspend fun addUser(age: Int, weight: Double, genderId: Int, addToServer: Boolean)
    suspend fun setUserName(newName: String)
    suspend fun logout()
    suspend fun setWeight(newValue: Double)
    suspend fun setGender(newValue: Int)
    suspend fun synchronizeUserDetails()
}
