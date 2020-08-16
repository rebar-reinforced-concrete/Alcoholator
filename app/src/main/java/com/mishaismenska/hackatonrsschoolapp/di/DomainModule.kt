package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.domain.*
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.domain.logic.CalculationManagerImpl
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
    fun provideUpdateWeightUseCase(updateWeightUseCaseImpl: UpdateWeightUseCaseImpl): UpdateWeightUseCase

    @Binds
    fun provideUpdateNameUseCase(updateNameUseCaseImpl: UpdateUserNameUseCaseImpl): UpdateUserNameUseCase

    @Binds
    fun provideUpdateUserGenderUseCase(updateUserGenderUseCaseImpl: UpdateUserGenderUseCaseImpl): UpdateUserGenderUseCase

    @Binds
    fun provideGetUserWithDrinksUseCase(getUserWIthDrinksUseCaseImpl: GetUserWithDrinksUseCaseImpl): GetUserWIthDrinksUseCase

    @Binds
    fun provideDetermineMaximalAlcoholConcentrationExceededUseCase(
        determineMaximalAlcoholConcentrationExceededUseCaseImpl: DetermineMaximalAlcoholConcentrationExceededUseCaseImpl
    ): DetermineMaximalAlcoholConcentrationExceededUseCase

    @Binds
    fun provideExistenceUseCase(existenceUseCase: GetUserExistenceUseCaseImpl): GetUserExistenceUseCase
}
