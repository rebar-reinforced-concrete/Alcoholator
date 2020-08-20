package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.VolumeTitleRepositoryImpl
import com.mishaismenska.hackatonrsschoolapp.domain.*
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.domain.logic.CalculationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.domain.logic.MeasureSystemsManagerImpl
import com.mishaismenska.hackatonrsschoolapp.domain.logic.UnitConverterImpl
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {
    @Binds
    @Singleton
    fun provideCalculationsManager(calculationManager: CalculationManagerImpl): CalculationManager

    @Binds
    fun provideGetStateUseCase(getStateUseCaseImpl: GetStateUseCaseImpl): GetStateUseCase

    @Binds
    fun provideGetDrinksUseCase(getDrinksUseCaseImpl: GetDrinksUseCaseImpl): GetDrinksUseCase

    @Binds
    fun provideRemoveDrinkUseCase(removeDrinkUseCaseImpl: RemoveDrinkUseCaseImpl): RemoveDrinkUseCase

    @Binds
    fun provideAddDrinkUseCase(addDrinkUseCaseImpl: AddDrinkUseCaseImpl): AddDrinkUseCase

    @Binds
    fun provideAddUserUseCase(addUserUseCaseImpl: AddUserUseCaseImpl): AddUserUseCase

    @Binds
    fun provideGetUserForSettingsUseCase(getUserForSettingsUseCaseImpl: GetUserForSettingsUseCaseImpl): GetUserForSettingsUseCase

    @Binds
    fun provideResetDataBaseUseCase(resetDataBaseUseCaseImpl: ResetDataBaseUseCaseImpl): ResetDataBaseUseCase

    @Binds
    fun provideSetUserWeightUseCase(setUserWeightUseCaseImpl: SetUserWeightUseCaseImpl): SetUserWeightUseCase

    @Binds
    fun provideSetUserNameUseCase(setUserNameUseCaseImpl: SetUserNameUseCaseImpl): SetUserNameUseCase

    @Binds
    fun provideSetUserGenderUseCase(setUserGenderUseCaseImpl: UpdateUserGenderUseCaseImpl): SetUserGenderUseCase

    @Binds
    fun provideDetermineMaximalAlcoholConcentrationExceededUseCase(
        checkIfMaxConcentrationExceededUseCaseImpl: CheckIfMaxConcentrationExceededUseCaseImpl
    ): CheckIfConcentrationExceededUseCase

    @Binds
    fun provideExistenceUseCase(existenceUseCase: GetUserExistenceUseCaseImpl): GetUserExistenceUseCase

    @Binds
    fun provideConvertIfRequiredUseCase(convertIfRequiredUseCase: ConvertIfRequiredUseCaseImpl): ConvertIfRequiredUseCase

    @Binds
    fun provideCalculateIndexesUseCase(calculateIndexesUseCase: CalculateIndexesUseCaseImpl): CalculateIndexesUseCase

    @Binds
    fun provideCheckIfUserAgeValidUseCase(checkIfUserAgeValidUseCaseImpl: CheckIfUserAgeValidUseCaseImpl): CheckIfUserAgeValidUseCase

    @Binds
    fun provideCheckIfUserWeightValidUseCase(checkIfUserWeightValidUseCaseImpl: CheckIfUserWeightValidUseCaseImpl): CheckIfUserWeightValidUseCase

    @Binds
    @Singleton
    fun provideGendersUseCase(gendersUseCaseImpl: GetGendersUseCaseImpl): GetGendersUseCase

    @Binds
    @Singleton
    fun provideMeasureSystemsManager(measureSystemsManagerImpl: MeasureSystemsManagerImpl): MeasureSystemsManager

    @Binds
    @Singleton
    fun provideIsImperialMeasureSystemUseCase(isImperialMeasureSystemUseCaseImpl: IsImperialMeasureSystemUseCaseImpl): IsImperialMeasureSystemUseCase

    @Binds
    @Singleton
    fun provideGetUserChangedFlowUseCase(getUserChangedFlowUseCaseImpl: GetUserChangedFlowUseCaseImpl): GetUserChangedFlowUseCase

    @Binds
    @Singleton
    fun provideUnitConverter(unitConverterImpl: UnitConverterImpl): UnitConverter

    @Binds
    @Singleton
    fun provideParseSelectedVolumeUseCase(parseSelectedVolumeUseCaseImpl: ParseSelectedVolumeUseCaseImpl): ParseSelectedVolumeUseCase

    @Binds
    @Singleton
    fun provideVolumeTitleRepository(volumeTitleRepositoryImpl: VolumeTitleRepositoryImpl): VolumeTitleRepository

    @Binds
    @Singleton
    fun provideGetVolumeTitlesUseCase(getVolumeTitlesUseCaseImpl: GetVolumeTitlesUseCaseImpl): GetVolumeTitlesUseCase

    @Binds
    @Singleton
    fun provideGetVolumePresetsUseCase(getVolumePresetsUseCaseImpl: GetVolumePresetsUseCaseImpl): GetVolumePresetsUseCase
}
