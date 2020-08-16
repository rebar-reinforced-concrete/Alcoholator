package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfUserWeightValidUseCase
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import javax.inject.Inject

class CheckIfUserWeightValidUseCaseImpl @Inject constructor() : CheckIfUserWeightValidUseCase {
    override fun checkIfUserWeightValid(weightInKg: Int): Boolean = weightInKg >=
            AppConstants.MIN_POSSIBLE_USER_WEIGHT && weightInKg <= AppConstants.MAX_POSSIBLE_USER_WEIGHT
}
