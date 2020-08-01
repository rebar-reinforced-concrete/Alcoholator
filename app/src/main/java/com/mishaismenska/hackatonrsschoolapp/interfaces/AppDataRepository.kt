package com.mishaismenska.hackatonrsschoolapp.interfaces

import androidx.lifecycle.LiveData
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.User

interface AppDataRepository {
    fun getUserWithDrinks() : User
    fun addDrink(drink: Drink)
}
