package com.mishaismenska.hackatonrsschoolapp.interfaces

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.User

interface AppDataRepository {
    suspend fun getUserWithDrinks() : User
    suspend fun addDrink(drink: Drink)
    suspend fun deleteDrink(drink: Drink)
    suspend fun addUser(age: Int, weight: Measure, gender: Gender)
}
