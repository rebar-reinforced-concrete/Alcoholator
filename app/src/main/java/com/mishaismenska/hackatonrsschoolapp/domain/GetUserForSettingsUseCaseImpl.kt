package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.LocaleData
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.data.UnitConverter
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserSettingsUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants.imperialLocales
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import java.util.*

class GetUserForSettingsUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetUserForSettingsUseCase {
    override suspend fun getUser(): UserSettingsUIModel {
        var user: UserSettingsUIModel? = null
        appDataRepository.getUser().take(1).collect {
            val unitWeight = if (Locale.getDefault().country in imperialLocales)
                Measure(UnitConverter.kgToLb(it[0].weightValueInKg), MeasureUnit.POUND)
            else
                Measure(it[0].weightValueInKg, it[0].unit)
            user = UserSettingsUIModel(
                unitWeight,
                it[0].userName,
                it[0].genderId
            )
        }
        return user!!
    }
}
