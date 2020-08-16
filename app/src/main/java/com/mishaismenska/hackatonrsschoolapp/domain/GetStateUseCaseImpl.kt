package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserStateCache
import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import java.time.LocalDate
import javax.inject.Inject

class GetStateUseCaseImpl @Inject constructor(
    private val calculationManager: CalculationManager,
    private val appDataRepository: AppDataRepository,
    private val userStateCache: UserStateCache
) : GetStateUseCase {

    override suspend fun getState(recalculationIsNeeded: Boolean): UserStateUIModel {
        val oldState = userStateCache.retrieveUserState()
        if (oldState == null || recalculationIsNeeded) {

            val user = appDataRepository.getUserWithDrinks()
            val currentUserAge =
                LocalDate.now().year - user.user.createdOn.year + user.user.ageOnCreation
            val userStateDomainModel = calculationManager.determineState(
                UserWithDrinksDomainModel(
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
            )
            userStateCache.storeUserState(
                //TODO: move to data layer
                UserStateDataModel(
                    userStateDomainModel.alcoholConcentration,
                    userStateDomainModel.alcoholDepletionDuration,
                    userStateDomainModel.lastUpdateTime
                )
            )
        } else {
            val newState = calculationManager.updateState(
                UserStateDomainModel(
                    oldState.alcoholConcentration,
                    oldState.alcoholDepletionDuration,
                    oldState.lastUpdateTime
                )
            )
            userStateCache.storeUserState(
                //TODO: move to data layer
                UserStateDataModel(
                    newState.alcoholConcentration,
                    newState.alcoholDepletionDuration,
                    newState.lastUpdateTime
                )
            )
        }
        val userStateDataModel = userStateCache.retrieveUserState()!!
        return UserStateUIModel(
            userStateDataModel.alcoholConcentration,
            userStateDataModel.alcoholDepletionDuration,
            calculationManager.getBehaviourFromConcentration(userStateDataModel.alcoholConcentration)
        )
    }
}
