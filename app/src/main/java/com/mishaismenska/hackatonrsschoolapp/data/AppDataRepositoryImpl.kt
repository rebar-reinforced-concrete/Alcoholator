package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import android.icu.util.Measure
import android.util.Log
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.database.AppDatabase
import com.mishaismenska.hackatonrsschoolapp.data.models.*
import com.mishaismenska.hackatonrsschoolapp.data.networking.DebugRetrofitService
import com.mishaismenska.hackatonrsschoolapp.data.networking.DrinksRetrofitService
import com.mishaismenska.hackatonrsschoolapp.data.networking.UserRetrofitService
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

const val googleId = "113891726776415900498"

class AppDataRepositoryImpl @Inject constructor(
    private val context: Context,
    private val userRetrofitService: UserRetrofitService,
    private val debugRetrofitService: DebugRetrofitService,
    private val drinksRetrofitService: DrinksRetrofitService,
    private val networkManager: NetworkManager
) :
    AppDataRepository {

    private val dao = AppDatabase.getDatabase(context).dao()

    override suspend fun getUserWithDrinks(): UserWithDrinksDomainModel? {
        //get data from network and db. Compare. Give the newest result to domain. Update the oldest
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

    override suspend fun synchronizeData(){
        val networkUser: UserWithDrinksJsonDataModel = withContext(Dispatchers.IO) {
            drinksRetrofitService.getUserWithDrinks(googleId)
        }
        val dbUser: UserWithDrinksDataModel? = withContext(Dispatchers.IO){
            dao.getUserWithDrinks()
        }
        if(dbUser == null){
            addUser(networkUser.userAgeOnCreation, networkUser.weightInKg, networkUser.genderId)
        } else if(dbUser.user.alteredTimeStamp < networkUser.userAlteredTimestamp){
            setGender(networkUser.genderId)
            setWeight(networkUser.weightInKg)
            setUserName(networkUser.userName)
        } else if(dbUser.user.alteredTimeStamp < networkUser.userAlteredTimestamp) {
            userRetrofitService.alterUserGender(UserAlterGenderJsonDataModel(googleId, dbUser.user.genderId))
            userRetrofitService.alterUserWeight(UserAlterWeightJsonDataModel(googleId, dbUser.user.weightValueInKg))
            userRetrofitService.alterUserName(UserAlterNameJsonDataModel(googleId, dbUser.user.userName))
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

    override fun addDrink(drinkDomainModel: DrinkDomainModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        if(networkManager.isNetworkOnline()) scope.launch {
            drinksRetrofitService.addDrink(
                DrinkAddDrinkJsonDataModel(
                    googleId, drinkDomainModel.type.ordinal, drinkDomainModel.dateTaken.toEpochSecond(
                        OffsetDateTime.now().offset
                    ), drinkDomainModel.volume.number.toInt(), drinkDomainModel.eaten
                )
            )
        }
        scope.launch {
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
    }

    override suspend fun deleteDrink(recyclerPosition: Int) {
        dao.getDrinks().collect {
            dao.deleteDrink(it[recyclerPosition])
        }
    }

    override suspend fun addUser(age: Int, weight: Double, genderId: Int) {
        if(networkManager.isNetworkOnline())
            userRetrofitService.addUser(UserJsonDataModel(LocalDate.now().toEpochDay(), genderId, weight, googleId, age))
        dao.insertUser(
            UserDataModel(
                LocalDate.now().toEpochDay(),
                LocalDate.now(),
                age,
                genderId,
                weight,
                context.getString(R.string.default_name),
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            )
        )
        Log.d("users", debugRetrofitService.getAllUsers().body().toString())
    }

    override suspend fun setUserName(newName: String) {
        if(networkManager.isNetworkOnline())
            userRetrofitService.alterUserName(UserAlterNameJsonDataModel(googleId, newName))
        dao.setName(newName)
    }

    override suspend fun setWeight(newValue: Double) {
        if(networkManager.isNetworkOnline())
            userRetrofitService.alterUserWeight(UserAlterWeightJsonDataModel(googleId, newValue))
        dao.setWeight(newValue)
    }

    override suspend fun setGender(newValue: Int) {
        if(networkManager.isNetworkOnline())
            userRetrofitService.alterUserGender(UserAlterGenderJsonDataModel(googleId, newValue))
        dao.setGender(newValue)
    }

    override suspend fun logout() {
        dao.resetUser()
        dao.resetDrinks()
    }
}
