package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserStateCache
import com.mishaismenska.hackatonrsschoolapp.data.models.UserStateDataModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPresets
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetStateUseCaseImpl @Inject constructor(
    private val calculationManager: CalculationManager,
    private val appDataRepository: AppDataRepository,
    private val userStateCache: UserStateCache
) : GetStateUseCase {

    override suspend fun getState(): UserStateUIModel {
        val oldState = userStateCache.retrieveUserState()
        if(oldState == null){
            appDataRepository.getUser().collect { user ->
                appDataRepository.getDrinks().collect { drinks ->
                    val userStateDomainModel = calculationManager.determineState(
                        UserDomainModel(
                            user[0].ageOnCreation,
                            Measure(user[0].weight, user[0].unit),
                            Gender.values()[user[0].gender]
                        ), drinks.map {
                            DrinkDomainModel(
                                DrinkPresets.values()[it.type],
                                it.dateTaken,
                                Measure(it.volume, it.unit),
                                it.eaten
                            )
                        })
                    userStateCache.storeUserState(
                        UserStateDataModel(
                            userStateDomainModel.alcoholConcentration,
                            userStateDomainModel.soberTime,
                            userStateDomainModel.lastUpdateTime
                        )
                    )
                }
            }
        } else{
            val newState = calculationManager.updateState(UserStateDomainModel(oldState.alcoholConcentration, oldState.soberTime, oldState.lastUpdateTime))
            userStateCache.storeUserState(UserStateDataModel(newState.alcoholConcentration, newState.soberTime, newState.lastUpdateTime))
        }
        val userStateDataModel = userStateCache.retrieveUserState()!!
        return UserStateUIModel(
            userStateDataModel.alcoholConcentration,
            userStateDataModel.soberTime,
            calculationManager.getBehaviourFromConcentration(userStateDataModel.alcoholConcentration)
        )
    }
}
