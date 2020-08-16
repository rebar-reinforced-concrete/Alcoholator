package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours

interface CalculationManager {

    //TODO: replace this shit with user class after removing livedata from repository
    //calculates initial state. Should be called ONLY when app is started
    fun determineState(userWithDrinksDomainModel: UserWithDrinksDomainModel): UserStateDomainModel

    fun determineIfUserStillCanDrink(alcoholConcentration: Double): Boolean

    //adjusts behaviour, alcohol concentration and sobering time basing only on time difference
    fun updateState(oldStateDomainModel: UserStateDomainModel): UserStateDomainModel

    fun getBehaviourFromConcentration(concentration: Double): Behaviours
}
