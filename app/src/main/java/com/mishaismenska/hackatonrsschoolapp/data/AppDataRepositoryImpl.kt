package com.mishaismenska.hackatonrsschoolapp.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.MutableLiveData
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import java.time.LocalDate

class AppDataRepositoryImpl : AppDataRepository {
    private val drinks = MutableLiveData<List<Drink>>()
    private val user = User(123, Measure(123, MeasureUnit.KILOGRAM), Gender.MALE_IDENTIFIES_AS_FEMALE, drinks)

    override fun getUserWithDrinks(): User {
        drinks.postValue(listOf(Drink(DrinkType.CHAMPAGNE, LocalDate.now(), Measure(122, MeasureUnit.MILLILITER)),
            Drink(DrinkType.COGNAC, LocalDate.now(), Measure(500, MeasureUnit.MILLILITER))))
        return user
    }

    override fun addDrink(drink: Drink) {
        val oldDrinks = drinks.value!!
        val newDrinks = mutableListOf<Drink>()
        newDrinks.addAll(oldDrinks)
        newDrinks.add(drink)
        drinks.postValue(newDrinks)
    }
}
