package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserForSettingsUseCaseImpl @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val measureSystemsManager: MeasureSystemsManager
) :
    GetUserForSettingsUseCase {
    override suspend fun getUser(): Flow<UserSettingsUIModel?> = appDataRepository.getUser().map {
        if (it.isNullOrEmpty()) {
            null
        } else {
            val user = it[0]
            UserSettingsUIModel(
                measureSystemsManager.convertUserWeightToImperialIfRequired(user.weight),
                user.name,
                user.gender.ordinal
            )
        }
    }
}
