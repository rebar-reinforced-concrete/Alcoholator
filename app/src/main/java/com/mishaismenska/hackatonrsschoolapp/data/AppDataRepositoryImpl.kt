package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject


class AppDataRepositoryImpl @Inject constructor(private val context: Context) :
    AppDataRepository {

    private val dao = AppDatabase.getDatabase(context).dao()

    override suspend fun getUser(): Flow<List<UserDataModel>> {
        return dao.getUser()
    }

    override suspend fun getDrinks(): Flow<List<DrinkDataModel>> {
        return dao.getDrinks()
    }

    override suspend fun addDrink(drinkDomainModel: DrinkDomainModel) {
        dao.getUser().collect{
            dao.insertDrink(
                DrinkDataModel(
                    drinkDomainModel.date.toEpochSecond(ZoneOffset.UTC),
                    it[0].userId,
                    drinkDomainModel.type.ordinal,
                    drinkDomainModel.date,
                    drinkDomainModel.volume.number as Int,
                    drinkDomainModel.volume.unit,
                    drinkDomainModel.eaten
                )
            )
        }
    }

    override suspend fun deleteDrink(recyclerPosition: Int) { dao.getDrinks().collect {
            dao.deleteDrink(it[recyclerPosition])
        }
    }

    override suspend fun addUser(age: Int, weight: Measure, gender: Gender) {
        dao.insertUser(
            UserDataModel(
                LocalDate.now().toEpochDay(),
                LocalDate.now(),
                age,
                gender.ordinal,
                weight.number as Int,
                weight.unit
            )
        )
    }

    override suspend fun reset(){
        dao.resetUser()
        dao.resetDrinks()
    }

    override suspend fun updateWeight(newValue: Int) {
        dao.updateWeight(newValue)
    }
}
