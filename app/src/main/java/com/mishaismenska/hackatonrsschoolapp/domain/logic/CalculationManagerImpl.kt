package com.mishaismenska.hackatonrsschoolapp.domain.logic

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class CalculationManagerImpl @Inject constructor() :
    CalculationManager {

    override fun determineState(
        userDomainModel: UserDomainModel,
        drinkDomainModels: List<DrinkDomainModel>
    ): UserStateDomainModel {
        var concentration = 0.0
        for (drink in drinkDomainModels) {
            var currentDrinkConcentration = getMaxConcentrationForDrink(userDomainModel, drink)
            val hourDifference =
                Duration.between(drink.date, LocalDateTime.now()).toMinutes().toDouble() / 60.0
            currentDrinkConcentration -= hourDifference * 0.15
            if (currentDrinkConcentration > 0) concentration += currentDrinkConcentration
        }
        return UserStateDomainModel(
            concentration,
            Duration.ofMinutes((concentration / 0.15 * 60).toLong())
        )
    }

    override fun determineIfUserStillCanDrink(alcoholConcentration: Double): Boolean =
        alcoholConcentration < AppConstants.maxPossibleAlcoholConcentration

    override fun updateState(oldStateDomainModel: UserStateDomainModel): UserStateDomainModel {
        val oldTime = oldStateDomainModel.lastUpdateTime
        val currentTime = LocalDateTime.now()
        val duration = Duration.between(oldTime, currentTime)
        val newSoberingTime = oldStateDomainModel.soberTime.minus(duration)
        val hourDifference = duration.toMinutes().toDouble() / 60.0
        val newConcentration = oldStateDomainModel.alcoholConcentration - hourDifference * 0.15
        return UserStateDomainModel(newConcentration, newSoberingTime, currentTime)
    }


    override fun getBehaviourFromConcentration(concentration: Double) =
           when {
                concentration < Behaviours.ALMOST_NORMAL.lowestConcentration -> Behaviours.SOBER
                concentration < Behaviours.EUPHORIC.lowestConcentration -> Behaviours.ALMOST_NORMAL
                concentration < Behaviours.DISINHIBITIONS.lowestConcentration -> Behaviours.EUPHORIC
                concentration < Behaviours.EXPRESSIVENESS.lowestConcentration -> Behaviours.DISINHIBITIONS
                concentration < Behaviours.STUPOR.lowestConcentration -> Behaviours.EXPRESSIVENESS
                concentration < Behaviours.UNCONSCIOUS.lowestConcentration -> Behaviours.STUPOR
                concentration < Behaviours.BLACKOUT.lowestConcentration -> Behaviours.UNCONSCIOUS
                concentration < Behaviours.DEAD.lowestConcentration -> Behaviours.BLACKOUT
                else -> Behaviours.DEAD
            }

    private fun getMaxConcentrationForDrink(
        userDomainModel: UserDomainModel,
        drinkDomainModel: DrinkDomainModel
    ): Double {
        val m =
            drinkDomainModel.volume.number.toDouble() * drinkDomainModel.type.percentage / 100.0 * (if (drinkDomainModel.eaten) 0.7 else 0.9)
        val r =
            if (userDomainModel.gender.isMale) 0.7 else 0.6
        return m / userDomainModel.weight.number.toDouble() / r
    }
}
