package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserWIthDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import java.time.LocalDate
import javax.inject.Inject

class GetUserWithDrinksUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetUserWIthDrinksUseCase {
    override suspend fun getUserWithDrinks(): UserWithDrinksDomainModel {
        val user = appDataRepository.getUserWithDrinks()
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
            Measure(user.user.weightValueInKg, MeasureUnit.KILOGRAM),
            Gender.values()[user.user.genderId]
        )
    }
}
