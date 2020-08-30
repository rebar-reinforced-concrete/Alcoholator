package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.database.AppDatabase
import com.mishaismenska.hackatonrsschoolapp.data.models.*
import com.mishaismenska.hackatonrsschoolapp.data.networking.DrinksRetrofitService
import com.mishaismenska.hackatonrsschoolapp.data.networking.UserRetrofitService
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import javax.inject.Inject

class AppDataRepositoryImpl @Inject constructor(
    private val context: Context,
    private val userRetrofitService: UserRetrofitService,
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

    private fun getToken() = GoogleSignIn.getLastSignedInAccount(context)!!.idToken!!

    override suspend fun synchronizeUserDetails() {
        val googleId = getToken()
        val networkUser: UserWithDrinksJsonDataModel = withContext(Dispatchers.IO) {
            drinksRetrofitService.getUserWithDrinks(googleId)
        }
        val dbUser: UserWithDrinksDataModel? = withContext(Dispatchers.IO) {
            dao.getUserWithDrinks()
        }
        when {
            dbUser == null -> {
                addUser(networkUser.ageOnCreation, networkUser.weightInKg, networkUser.genderId, false)
            }
            dbUser.user.alteredTimeStamp < networkUser.userAlteredTimestamp -> {
                setGender(networkUser.genderId)
                setWeight(networkUser.weightInKg)
                setUserName(networkUser.userName)
            }
            dbUser.user.alteredTimeStamp > networkUser.userAlteredTimestamp -> {
                userRetrofitService.alterUserGender(UserAlterGenderJsonDataModel(googleId, dbUser.user.genderId))
                userRetrofitService.alterUserWeight(UserAlterWeightJsonDataModel(googleId, dbUser.user.weightValueInKg))
                userRetrofitService.alterUserName(UserAlterNameJsonDataModel(googleId, dbUser.user.userName))
            }
        }
        synchronizeDrinks(networkUser)
    }

    private suspend fun synchronizeDrinks(networkUser: UserWithDrinksJsonDataModel) {
        val dbDrinks = dao.getDrinks()
        dbDrinks.take(1).collect { drinks ->
            if (drinks.isNullOrEmpty()) transferServerDrinksToLocal(networkUser)
            else {
                dao.getUser().take(1).collect { userList ->
                    val localUser = userList[0]
                    compareAndUpdateDrinks(drinks, networkUser, localUser)
                }
            }
        }
    }

    private suspend fun compareAndUpdateDrinks(
        dbDrinks: List<DrinkDataModel>,
        networkUser: UserWithDrinksJsonDataModel,
        localUser: UserDataModel
    ) {
        val googleId = getToken()
        when {
            localUser.drinksAlteredTimestamp > networkUser.drinksAlteredTimestamp -> transferLocalDrinksToServer(networkUser, dbDrinks, googleId)
            localUser.drinksAlteredTimestamp < networkUser.drinksAlteredTimestamp -> transferServerDrinksToLocal(networkUser)
        }
    }

    private suspend fun transferServerDrinksToLocal(networkUser: UserWithDrinksJsonDataModel) {
        dao.resetDrinks()
        networkUser.drinks.map { netDrink ->
            addDrink(
                DrinkDomainModel(
                    DrinkPreset.values()[netDrink.typeId],
                    LocalDateTime.ofEpochSecond(netDrink.dateTimeTaken, 0, OffsetDateTime.now().offset),
                    Measure(netDrink.volumeInMl, MeasureUnit.MILLILITER),
                    netDrink.eaten
                ), false
            )
        }
    }

    private suspend fun transferLocalDrinksToServer(networkUser: UserWithDrinksJsonDataModel, dbDrinks: List<DrinkDataModel>, googleId: String) {
        networkUser.drinks.map { netDrink ->
            drinksRetrofitService.removeDrink(
                DrinkAddDrinkJsonDataModel(googleId, netDrink.typeId, netDrink.dateTimeTaken, netDrink.volumeInMl, netDrink.eaten)
            )
        }
        dbDrinks.map { dbDrink ->
            drinksRetrofitService.addDrink(
                DrinkAddDrinkJsonDataModel(
                    googleId,
                    dbDrink.typeId,
                    dbDrink.dateTaken.toEpochSecond(OffsetDateTime.now().offset),
                    dbDrink.volumeValueInMl,
                    dbDrink.eaten
                )
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
            it.map { dbDrink ->
                DrinkDomainModel(
                    DrinkPreset.values()[dbDrink.typeId],
                    dbDrink.dateTaken,
                    Measure(dbDrink.volumeValueInMl, dbDrink.unit),
                    dbDrink.eaten
                )
            }
        }
    }


    override fun addDrink(drinkDomainModel: DrinkDomainModel, addToServer: Boolean) {
        val scope = CoroutineScope(Dispatchers.IO)
        if (addToServer) addDrinkToServer(scope, drinkDomainModel)
        scope.launch {
            dao.getUser().take(1).collect {
                dao.setDrinksAlteredTimestamp(drinkDomainModel.dateTaken.toEpochSecond(OffsetDateTime.now().offset)) // added this field so we dont need to find the latest drink each time
                dao.insertDrink(
                    DrinkDataModel(
                        drinkDomainModel.dateTaken.toEpochSecond(OffsetDateTime.now().offset),
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

    private fun addDrinkToServer(scope: CoroutineScope, drinkDomainModel: DrinkDomainModel) {
        if (networkManager.isNetworkOnline()) scope.launch {
            val googleId = getToken()
            drinksRetrofitService.addDrink(
                DrinkAddDrinkJsonDataModel(
                    googleId, drinkDomainModel.type.ordinal, drinkDomainModel.dateTaken.toEpochSecond(
                        OffsetDateTime.now().offset
                    ), drinkDomainModel.volume.number.toInt(), drinkDomainModel.eaten
                )
            )
        }
    }

    override suspend fun deleteDrink(recyclerPosition: Int) {
        dao.getDrinks().collect {
            dao.deleteDrink(it[recyclerPosition])
        }
    }

    override suspend fun addUser(age: Int, weight: Double, genderId: Int, addToServer: Boolean) {
        var name = context.getString(R.string.default_name)
        if (networkManager.isNetworkOnline()) {
            val googleId = getToken()
            if (addToServer) userRetrofitService.addUser(UserJsonDataModel(LocalDate.now().toEpochDay(), genderId, weight, googleId, age))
            name = drinksRetrofitService.getUserWithDrinks(googleId).userName
        }
        dao.insertUser(
            UserDataModel(
                LocalDate.now().toEpochDay(),
                LocalDate.now(),
                age,
                genderId,
                weight,
                name,
                LocalDateTime.now().toEpochSecond(OffsetDateTime.now().offset),
                LocalDateTime.now().toEpochSecond(OffsetDateTime.now().offset)
            )
        )
    }

    override suspend fun setUserName(newName: String) {
        val googleId = getToken()
        if (networkManager.isNetworkOnline())
            userRetrofitService.alterUserName(UserAlterNameJsonDataModel(googleId, newName))
        dao.setName(newName)
    }

    override suspend fun setWeight(newValue: Double) {
        val googleId = getToken()
        if (networkManager.isNetworkOnline())
            userRetrofitService.alterUserWeight(UserAlterWeightJsonDataModel(googleId, newValue))
        dao.setWeight(newValue)
    }

    override suspend fun setGender(newValue: Int) {
        val googleId = getToken()
        if (networkManager.isNetworkOnline())
            userRetrofitService.alterUserGender(UserAlterGenderJsonDataModel(googleId, newValue))
        dao.setGender(newValue)
    }

    override suspend fun logout() {
        dao.resetUser()
        dao.resetDrinks()
    }
}
