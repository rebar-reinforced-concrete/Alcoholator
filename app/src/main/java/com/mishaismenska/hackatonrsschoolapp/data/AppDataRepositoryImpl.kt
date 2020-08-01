package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import android.icu.util.Measure
import androidx.lifecycle.MutableLiveData
import com.mishaismenska.hackatonrsschoolapp.data.entities.DrinkEntity
import com.mishaismenska.hackatonrsschoolapp.data.entities.UserEntity
import com.mishaismenska.hackatonrsschoolapp.data.entities.UserWithDrinks
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import java.time.LocalDate
import java.time.Period
import java.time.ZoneOffset
import javax.inject.Inject


class AppDataRepositoryImpl @Inject constructor(private val context: Context) :
    AppDataRepository {

    private var userEntity: UserWithDrinks? = null
    private val dao = AppDatabase.getDatabase(context).dao()
    override suspend fun getUserWithDrinks(): User {
        return dao.getUserWithDrinksEntity().let {
            val userAge =
                it.user.ageOnCreation + Period.between(it.user.createdOn, LocalDate.now()).years
            return@let User(
                userAge,
                Measure(it.user.weight, it.user.unit),
                Gender.values()[it.user.gender],
                MutableLiveData(it.drinks.map { entity ->
                    Drink(
                        DrinkType.values()[entity.type],
                        entity.dateTaken,
                        Measure(entity.volume, entity.unit)
                    )
                })
            )
        }
    }

    override suspend fun addDrink(drink: Drink) {
        userEntity?.let {
            dao.insertDrink(
                DrinkEntity(
                    drink.date.toEpochSecond(ZoneOffset.UTC),
                    it.user.userId,
                    drink.type.ordinal,
                    drink.date,
                    drink.volume.number as Int,
                    drink.volume.unit
                )
            )
        }

    }

    override suspend fun deleteDrink(drink: Drink) {
        userEntity?.let {
            dao.deleteDrink(
                DrinkEntity(
                    drink.date.toEpochSecond(ZoneOffset.UTC),
                    it.user.userId,
                    drink.type.ordinal,
                    drink.date,
                    drink.volume.number as Int,
                    drink.volume.unit
                )
            )
        }
    }

    override suspend fun addUser(age: Int, weight: Measure, gender: Gender) {
        dao.insertUser(
            UserEntity(
                LocalDate.now().toEpochDay(),
                LocalDate.now(),
                age,
                gender.ordinal,
                weight.number as Int,
                weight.unit
            )
        )
        this.userEntity = dao.getUserWithDrinksEntity()
    }
}
