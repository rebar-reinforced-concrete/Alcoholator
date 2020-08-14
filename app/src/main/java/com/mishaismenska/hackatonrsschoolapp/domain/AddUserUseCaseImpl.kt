package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel
import javax.inject.Inject

class AddUserUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : AddUserUseCase {
    override suspend fun addUser(userSubmissionUIModel: UserSubmissionUIModel) {
        appDataRepository.addUser(userSubmissionUIModel.age, userSubmissionUIModel.weight, userSubmissionUIModel.gender)
    }
}
