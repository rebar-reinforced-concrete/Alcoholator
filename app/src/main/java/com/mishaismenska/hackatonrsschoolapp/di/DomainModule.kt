package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.domain.*
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.presentation.GetUserForSettingsUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.GetUserForSettingsUseCase
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun provideGetStateUseCase(getStateUseCaseImpl: GetStateUseCaseImpl): GetStateUseCase

    @Binds
    fun provideGetDrinksUseCase(getDrinkUseCaseImpl: GetDrinkUseCaseImpl): GetDrinksUseCase

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
    fun provideUpdateWeightUseCase(updateWeightUseCaseImpl: UpdateWeightUseCaseImpl): UpdateWeightUseCase

    @Binds
    fun provideDetermineMaximalAlcoholConcentrationExceededUseCase(determineMaximalAlcoholConcentrationExceededUseCaseImpl: DetermineMaximalAlcoholConcentrationExceededUseCaseImpl): DetermineMaximalAlcoholConcentrationExceededUseCase
}
