package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behavior

interface CalculationManager {

    fun determineState(userWithDrinksDomainModel: UserWithDrinksDomainModel): UserStateDomainModel

    fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean

    fun updateState(oldStateDomainModel: UserStateDomainModel): UserStateDomainModel

    fun getBehaviourFromConcentration(concentration: Double): Behavior
}
