package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel

interface GetStateUseCase {
    suspend fun getState(): UserStateUIModel
}
