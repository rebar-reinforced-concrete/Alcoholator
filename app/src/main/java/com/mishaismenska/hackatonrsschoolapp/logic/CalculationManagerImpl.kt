package com.mishaismenska.hackatonrsschoolapp.logic

import android.icu.util.Measure
import android.util.Log
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
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
        var startTime = LocalDateTime.now()
        var concentration = 0.0
        for (drink in drinks) {
            val m =
                drink.volume.number.toDouble() * drink.type.percentage / 100.0 * (if (drink.eaten) 0.7 else 0.9)
            val r =
                if (gender == Gender.MALE || gender == Gender.MALE_IDENTIFIES_AS_FEMALE) 0.7 else 0.6
            var currentDrinkConcentration = m / weight.number.toDouble() / r
            val hourDifference =
                Duration.between(drink.date, LocalDateTime.now()).toMinutes().toDouble() / 60.0
            currentDrinkConcentration -= hourDifference * 0.15
            if (currentDrinkConcentration > 0) concentration += currentDrinkConcentration
        }
        val userState: Behaviours =
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
        val end = LocalDateTime.now()
        Log.d("Time in millis: ", Duration.between(startTime, end).toMillis().toString())
        Log.d(
            "Sobering time",
            Duration.ofMinutes((concentration / 0.15 * 60).toLong()).toMillis().toString()
        )
        Log.d("Sobering time 2", ((concentration / 0.15 * 60).toLong()).toString())
        Log.d("concentration", concentration.toString())
        return UserState(
            concentration,
            Duration.ofMinutes((concentration / 0.15 * 60).toLong()),
            userState
        )
    }
}
