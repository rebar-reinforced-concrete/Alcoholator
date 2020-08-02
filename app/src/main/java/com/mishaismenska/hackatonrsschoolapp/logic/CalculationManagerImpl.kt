package com.mishaismenska.hackatonrsschoolapp.logic

import android.icu.util.Measure
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
            val m = drink.volume.number.toDouble() * percentages[drink.type]!!.toDouble() / 100.0
            val r = if (gender == Gender.MALE || gender == Gender.MALE_IDENTIFIES_AS_FEMALE) 0.7 else 0.6
            var currentDrinkConcentration = m / weight.number.toDouble() / r
            val hourDifference = Duration.between(LocalDateTime.now(), drink.date).toHours()
            currentDrinkConcentration -= hourDifference * 0.15
            if (currentDrinkConcentration > 0) concentration += currentDrinkConcentration
        }
        val userState: Behaviours =
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
        return UserState(
            concentration,
            Duration.ofMinutes((concentration / 0.15 * 60).toLong()),
            userState
        )
    }
}
