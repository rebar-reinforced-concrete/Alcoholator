package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfUserAgeValidUseCase
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import javax.inject.Inject

class CheckIfUserAgeValidUseCaseImpl @Inject constructor() : CheckIfUserAgeValidUseCase {
    override fun checkIfUserAgeValid(age: Int): Boolean = age >= AppConstants.MIN_POSSIBLE_USER_AGE
}
