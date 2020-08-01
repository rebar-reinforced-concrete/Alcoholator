package com.mishaismenska.hackatonrsschoolapp.interfaces

import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState

interface CalculationManager {
    fun determineState(user: User): UserState
}
