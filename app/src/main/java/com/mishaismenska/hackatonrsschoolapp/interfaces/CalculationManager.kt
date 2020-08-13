package com.mishaismenska.hackatonrsschoolapp.interfaces

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState

interface CalculationManager {

    //TODO: replace this shit with user class after removing livedata from repository
    //calculates initial state. Should be called ONLY when app is started
    fun determineState(age: Int, weight: Measure, gender: Gender, drinks: List<Drink>): UserState

    fun determineIfUserStillCanDrink(alcoholConcentration: Double): Boolean

    //adjusts behaviour, alcohol concentration and sobering time basing only on time difference
    fun updateState(oldState: UserState): UserState

    //recalculates state by adding new drink to it
    fun addDrinkToState(age: Int, weight: Measure, gender: Gender, oldState: UserState, drink: Drink): UserState
}
