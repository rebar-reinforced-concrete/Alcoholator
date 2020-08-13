package com.mishaismenska.hackatonrsschoolapp.logic

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.AppConstants
import com.mishaismenska.hackatonrsschoolapp.data.behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.data.percentages
import com.mishaismenska.hackatonrsschoolapp.interfaces.CalculationManager
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class CalculationManagerImpl @Inject constructor() : CalculationManager {

    override fun determineState(
        age: Int,
        weight: Measure,
        gender: Gender,
        drinks: List<Drink>
    ): UserState {
        var concentration = 0.0
        for (drink in drinks) {
            var currentDrinkConcentration = getMaxConcentrationForDrink(age, weight, gender, drink)
            val hourDifference =
                Duration.between(drink.date, LocalDateTime.now()).toMinutes().toDouble() / 60.0
            currentDrinkConcentration -= hourDifference * 0.15
            if (currentDrinkConcentration > 0) concentration += currentDrinkConcentration
        }
        val userState: Behaviours = getBehaviourFromConcentration(concentration)
        return UserState(
            concentration,
            Duration.ofMinutes((concentration / 0.15 * 60).toLong()),
            userState
        )
    }

    override fun determineIfUserStillCanDrink(alcoholConcentration: Double): Boolean {
        return alcoholConcentration < AppConstants.maxPossibleAlcoholConcentration
    }

    override fun updateState(oldState: UserState): UserState {
        val oldTime = oldState.lastUpdateTime
        val currentTime = LocalDateTime.now()
        val duration = Duration.between(oldTime, currentTime)
        val newSoberingTime = oldState.soberTime.minus(duration)
        val hourDifference = duration.toMinutes().toDouble() / 60.0
        val newConcentration = oldState.alcoholConcentration - hourDifference * 0.15
        val behavior = getBehaviourFromConcentration(newConcentration)
        return UserState(newConcentration, newSoberingTime, behavior, currentTime)
    }

    override fun addDrinkToState(
        age: Int,
        weight: Measure,
        gender: Gender,
        oldState: UserState,
        drink: Drink
    ): UserState {
        val updatedState = updateState(oldState)
        val currentTime = updatedState.lastUpdateTime
        val oldConcentration = updatedState.alcoholConcentration
        var currentDrinkConcentration = getMaxConcentrationForDrink(age, weight, gender, drink)
        val hourDifference =
            Duration.between(drink.date, LocalDateTime.now()).toMinutes().toDouble() / 60.0
        currentDrinkConcentration -= hourDifference * 0.15
        val newConcentration =
            if (currentDrinkConcentration > 0) oldConcentration + currentDrinkConcentration else oldConcentration
        val newSoberingTime = Duration.ofMinutes((newConcentration / 0.15 * 60).toLong())
        val behaviour = getBehaviourFromConcentration(newConcentration)
        return UserState(newConcentration, newSoberingTime, behaviour, currentTime)
    }


    private fun getBehaviourFromConcentration(concentration: Double) =
        when {
            concentration < behaviours[Behaviours.ALMOST_NORMAL]!! -> Behaviours.SOBER
            concentration < behaviours[Behaviours.EUPHORIC]!! -> Behaviours.ALMOST_NORMAL
            concentration < behaviours[Behaviours.DISINHIBITIONS]!! -> Behaviours.EUPHORIC
            concentration < behaviours[Behaviours.EXPRESSIVENESS]!! -> Behaviours.DISINHIBITIONS
            concentration < behaviours[Behaviours.STUPOR]!! -> Behaviours.EXPRESSIVENESS
            concentration < behaviours[Behaviours.UNCONSCIOUS]!! -> Behaviours.STUPOR
            concentration < behaviours[Behaviours.BLACKOUT]!! -> Behaviours.UNCONSCIOUS
            concentration < behaviours[Behaviours.DEAD]!! -> Behaviours.BLACKOUT
            else -> Behaviours.DEAD
        }

    private fun getMaxConcentrationForDrink(
        age: Int,
        weight: Measure,
        gender: Gender,
        drink: Drink
    ): Double {
        val m =
            drink.volume.number.toDouble() * percentages[drink.type]!!.toDouble() / 100.0 * (if (drink.eaten) 0.7 else 0.9)
        val r =
            if (gender == Gender.MALE || gender == Gender.MALE_IDENTIFIES_AS_FEMALE) 0.7 else 0.6
        return m / weight.number.toDouble() / r
    }
}
