package com.mishaismenska.hackatonrsschoolapp.presentation

import android.icu.util.Measure
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetUserForSettingsUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : GetUserForSettingsUseCase {
    override suspend fun getUser(): UserSettingsUIModel {
        var user: UserSettingsUIModel? = null
        appDataRepository.getUser().collect {
            user = UserSettingsUIModel(Measure(it[0].weight, it[0].unit))
        }
        return user!!
    }
}
