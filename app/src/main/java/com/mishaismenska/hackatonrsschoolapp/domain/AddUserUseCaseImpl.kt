package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Gender
import javax.inject.Inject

class AddUserUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val gendersRepository: GendersRepository,
    private val measureSystemsManager: MeasureSystemsManager
) : AddUserUseCase {
    override suspend fun addUser(userSubmissionUIModel: UserSubmissionUIModel) {
        appDataRepository.addUser(
            userSubmissionUIModel.age,
            measureSystemsManager.convertUserWeightToMetricIfRequired(userSubmissionUIModel.weight),
            parseGender(userSubmissionUIModel.gender)
        )
    }

    private fun parseGender(userGenderString: String): Gender {
        val genderStrings = gendersRepository.provideGenders()
        return when (userGenderString) {
            genderStrings[0] -> Gender.MALE
            genderStrings[1] -> Gender.FEMALE
            genderStrings[2] -> Gender.MALE_IDENTIFIES_AS_FEMALE
            genderStrings[3] -> Gender.FEMALE_IDENTIFIES_AS_MALE
            else -> Gender.UNCERTAIN
        }
    }
}
