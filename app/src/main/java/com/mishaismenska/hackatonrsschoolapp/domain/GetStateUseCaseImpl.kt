package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UserStateRepository
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import javax.inject.Inject

class GetStateUseCaseImpl @Inject constructor(
    private val calculationManager: CalculationManager,
    private val appDataRepository: AppDataRepository,
    private val userStateRepository: UserStateRepository
) : GetStateUseCase {

    override suspend fun getState(recalculationIsNeeded: Boolean): UserStateUIModel? {
        val oldState = userStateRepository.retrieveUserState()
        val user = appDataRepository.getUserWithDrinks()
        return if (user == null) null
        else {
            val newState = if (recalculationIsNeeded || oldState == null) {
                calculationManager.determineState(user)
            } else {
                calculationManager.updateState(oldState)
            }
            UserStateUIModel(
                newState.alcoholConcentration,
                newState.alcoholDepletionDuration,
                calculationManager.getBehaviourFromConcentration(newState.alcoholConcentration)
            )
        }
    }
}
