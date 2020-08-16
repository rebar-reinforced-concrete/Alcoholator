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
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPresets
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import java.time.LocalDate
import javax.inject.Inject

class GetStateUseCaseImpl @Inject constructor(
    private val calculationManager: CalculationManager,
    private val appDataRepository: AppDataRepository,
    private val userStateCache: UserStateCache
) : GetStateUseCase {

    /*
    * NOW LISTEN HERE. I do understand that this might sound completely stupid, but,
    * and I insist, but I tried, for once I tried to write some code that could be reused.
    * So I did. Unfortunately, I cannot use my previously-implemented GetUserWithDrinksUseCase
    * here, because it is another usecase. So. YOU GET WHAT YOU FUCKING DESERVE. A STRAIGHT FUCKING
    * CARBON COPY OF THAT USECASE HERE. I DON'T CARE ANY MORE, NOT A SINGLE BIT. YOU WANTED CLEAN
    * ARCHITECTURE, YOU'LL GET IT.
    * AS CLEAN AS A HIV-POSITIVE-CHEAP-WHISKEY-IN-A-DEN-BINGING-TUBERCULOSIS-INDUCING HOBO'S HANDKERCHIEF.
    * IT IS SO FUCKING CLEAN, SO MIRACULOUS, IT WOULD NOT BECOME ANY DIRTIER IF I WIPED MY
    * 500-POUND ASS WITH IT.
    * GOD DAMN, IM SO BAD AT THIS, ITS PITIFUL
    * */

    override suspend fun getState(): UserStateUIModel {
        val oldState = userStateCache.retrieveUserState()
        if (oldState == null) {

            val user = appDataRepository.getUserWithDrinks()
            val currentUserAge =
                LocalDate.now().year - user.user.createdOn.year + user.user.ageOnCreation
            val userStateDomainModel = calculationManager.determineState(
                UserWithDrinksDomainModel(
                    user.drinks.map {
                        DrinkDomainModel(
                            DrinkPresets.values()[it.type],
                            it.dateTaken,
                            Measure(it.volume, it.unit),
                            it.eaten
                        )
                    },
                    currentUserAge,
                    Measure(user.user.weight, MeasureUnit.KILOGRAM),
                    Gender.values()[user.user.gender]
                )
            )
            userStateCache.storeUserState(
                UserStateDataModel(
                    userStateDomainModel.alcoholConcentration,
                    userStateDomainModel.soberTime,
                    userStateDomainModel.lastUpdateTime
                )
            )
        } else {
            val newState = calculationManager.updateState(
                UserStateDomainModel(
                    oldState.alcoholConcentration,
                    oldState.soberTime,
                    oldState.lastUpdateTime
                )
            )
            userStateCache.storeUserState(
                UserStateDataModel(
                    newState.alcoholConcentration,
                    newState.soberTime,
                    newState.lastUpdateTime
                )
            )
        }
        val userStateDataModel = userStateCache.retrieveUserState()!!
        return UserStateUIModel(
            userStateDataModel.alcoholConcentration,
            userStateDataModel.soberTime,
            calculationManager.getBehaviourFromConcentration(userStateDataModel.alcoholConcentration)
        )
    }
}
