package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class AppDataRepositoryImpl @Inject constructor(private val context: Context) :
    AppDataRepository {

    private val dao = AppDatabase.getDatabase(context).dao()

    override suspend fun getUserWithDrinks(): UserWithDrinksDomainModel? {
        val user = dao.getUserWithDrinks()
        if (user == null) {
            return null
        } else {
            val currentUserAge = LocalDate.now().year - user.user.createdOn.year + user.user.ageOnCreation
            return UserWithDrinksDomainModel(
                user.drinks.map {
                    DrinkDomainModel(
                        DrinkPreset.values()[it.typeId],
                        it.dateTaken,
                        Measure(it.volumeValueInMl, it.unit),
                        it.eaten
                    )
                },
                currentUserAge,
                user.user.weightValueInKg,
                Gender.values()[user.user.genderId]
            )
        }
    }

    override suspend fun getUser(): Flow<List<UserDomainModel>> {
        return dao.getUser().map {
            it.map { user ->
                val currentUserAge = LocalDate.now().year - user.createdOn.year + user.ageOnCreation
                UserDomainModel(currentUserAge, user.weightValueInKg, Gender.values()[user.genderId], user.userName)
            }
        }
    }

    override suspend fun getDrinks(): Flow<List<DrinkDomainModel>> {
        return dao.getDrinks().map {
            it.map { drink ->
                DrinkDomainModel(DrinkPreset.values()[drink.typeId], drink.dateTaken, Measure(drink.volumeValueInMl, drink.unit), drink.eaten)
            }
        }
    }

    override suspend fun addDrink(drinkDomainModel: DrinkDomainModel) {
        dao.getUser().take(1).collect {
            dao.insertDrink(
                DrinkDataModel(
                    drinkDomainModel.dateTaken.toEpochSecond(ZoneOffset.UTC),
                    it[0].userId,
                    drinkDomainModel.type.ordinal,
                    drinkDomainModel.dateTaken,
                    drinkDomainModel.volume.number as Int,
                    drinkDomainModel.volume.unit,
                    drinkDomainModel.eaten
                )
            )
        }
    }

    override suspend fun deleteDrink(recyclerPosition: Int) {
        dao.getDrinks().collect {
            dao.deleteDrink(it[recyclerPosition])
        }
    }

    override suspend fun addUser(age: Int, weight: Double, gender: Gender) {
        dao.insertUser(
            UserDataModel(
                LocalDate.now().toEpochDay(),
                LocalDate.now(),
                age,
                gender.ordinal,
                weight,
                context.getString(R.string.default_name)
            )
        )
    }

    override suspend fun setUserName(newName: String) {
        dao.setName(newName)
    }

    override suspend fun reset() {
        dao.resetUser()
        dao.resetDrinks()
    }

    override suspend fun setWeight(newValue: Double) {
        dao.setWeight(newValue)
    }

    override suspend fun setGender(newValue: Int) {
        dao.setGender(newValue)
    }
}
