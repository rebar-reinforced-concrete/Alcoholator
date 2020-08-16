package com.mishaismenska.hackatonrsschoolapp.domain.logic

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behavior
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class CalculationManagerImpl @Inject constructor() :
    CalculationManager {

    override fun determineState(
        userWithDrinksDomainModel: UserWithDrinksDomainModel
    ): UserStateDomainModel {
        var concentration = 0.0
        for (drink in userWithDrinksDomainModel.drinks) {
            var currentDrinkConcentration =
                getMaxConcentrationForDrink(
                    userWithDrinksDomainModel,
                    drink
                ) // TODO: a bit stupid, just a tad though)
            val hourDifference =
                Duration.between(drink.dateTaken, LocalDateTime.now()).toMinutes().toDouble() / 60.0
            currentDrinkConcentration -= hourDifference * 0.15
            if (currentDrinkConcentration > 0) concentration += currentDrinkConcentration
        }
        return UserStateDomainModel(
            concentration,
            Duration.ofMinutes((concentration / 0.15 * 60).toLong())
        )
    }

    override fun determineIfUserCanDrink(alcoholConcentration: Double): Boolean =
        alcoholConcentration < AppConstants.maxPossibleAlcoholConcentration

    override fun updateState(oldStateDomainModel: UserStateDomainModel): UserStateDomainModel {
        val oldTime = oldStateDomainModel.lastUpdateTime
        val currentTime = LocalDateTime.now()
        val duration = Duration.between(oldTime, currentTime)
        val newSoberingTime = oldStateDomainModel.alcoholDepletionDuration.minus(duration)
        val hourDifference = duration.toMinutes().toDouble() / 60.0
        val newConcentration = oldStateDomainModel.alcoholConcentration - hourDifference * 0.15
        return UserStateDomainModel(newConcentration, newSoberingTime, currentTime)
    }

    override fun getBehaviourFromConcentration(concentration: Double) =
        when {
            concentration < Behavior.ALMOST_NORMAL.lowestConcentration -> Behavior.SOBER
            concentration < Behavior.EUPHORIC.lowestConcentration -> Behavior.ALMOST_NORMAL
            concentration < Behavior.DISINHIBITIONS.lowestConcentration -> Behavior.EUPHORIC
            concentration < Behavior.EXPRESSIVENESS.lowestConcentration -> Behavior.DISINHIBITIONS
            concentration < Behavior.STUPOR.lowestConcentration -> Behavior.EXPRESSIVENESS
            concentration < Behavior.UNCONSCIOUS.lowestConcentration -> Behavior.STUPOR
            concentration < Behavior.BLACKOUT.lowestConcentration -> Behavior.UNCONSCIOUS
            concentration < Behavior.DEAD.lowestConcentration -> Behavior.BLACKOUT
            else -> Behavior.DEAD
        }

    private fun getMaxConcentrationForDrink(
        userWithDrinksDomainModel: UserWithDrinksDomainModel,
        drinkDomainModel: DrinkDomainModel
    ): Double {
        val m =
            drinkDomainModel.volume.number.toDouble() * drinkDomainModel.type.percentage / 100.0 * (if (drinkDomainModel.eaten) 0.7 else 0.9)
        val r =
            if (userWithDrinksDomainModel.gender.isMale) 0.7 else 0.6
        return m / userWithDrinksDomainModel.weight.number.toDouble() / r
    }
}
