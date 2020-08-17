package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.domain.AddDrinkUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.AddUserUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.CheckIfMaxConcentrationExceededUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.CheckIfUserAgeValidUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.CheckIfUserWeightValidUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.GetDrinksUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.GetStateUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.GetUserExistenceUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.GetUserForSettingsUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.GetUserWithDrinksUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.RemoveDrinkUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.ResetDataBaseUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.SetUserNameUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.SetUserWeightUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.UpdateUserGenderUseCaseImpl
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddUserUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfConcentrationExceededUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfUserAgeValidUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfUserWeightValidUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserExistenceUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserWIthDrinksUseCase //TODO: fix big IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIi
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.RemoveDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ResetDataBaseUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserGenderUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserNameUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SetUserWeightUseCase
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
    fun provideSetUserWeightUseCase(setUserWeightUseCaseImpl: SetUserWeightUseCaseImpl): SetUserWeightUseCase

    @Binds
    fun provideSetUserNameUseCase(setUserNameUseCaseImpl: SetUserNameUseCaseImpl): SetUserNameUseCase

    @Binds
    fun provideSetUserGenderUseCase(setUserGenderUseCaseImpl: UpdateUserGenderUseCaseImpl): SetUserGenderUseCase

    @Binds
    fun provideGetUserWithDrinksUseCase(getUserWIthDrinksUseCaseImpl: GetUserWithDrinksUseCaseImpl): GetUserWIthDrinksUseCase

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
}
