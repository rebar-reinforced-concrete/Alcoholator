package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel

interface GetUserForSettingsUseCase {
    suspend fun getUser(): UserSettingsUIModel
}
