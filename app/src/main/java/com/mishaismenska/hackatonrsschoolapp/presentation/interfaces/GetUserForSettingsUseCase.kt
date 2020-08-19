package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import kotlinx.coroutines.flow.Flow

interface GetUserForSettingsUseCase {
    suspend fun getUser(): Flow<UserSettingsUIModel?>
}
