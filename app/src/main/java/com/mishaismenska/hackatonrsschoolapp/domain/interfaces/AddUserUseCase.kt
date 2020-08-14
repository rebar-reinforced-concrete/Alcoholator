package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSubmissionUIModel

interface AddUserUseCase {
    suspend fun addUser(userSubmissionUIModel: UserSubmissionUIModel)
}
