package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.logic.AppNotificationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.logic.CalculationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.logic.UserInputValidatingManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface BusinessLogicModule {
    @Binds
    fun provideUserInputValidatingManager(userInputValidationManagerImpl: UserInputValidatingManagerImpl): UserInputValidatingManager

    @Binds
    fun provideCalculationsManager(calculationManager: CalculationManagerImpl): CalculationManager

    @Binds
    fun provideAppNotificationManager(appNotificationManagerImpl: AppNotificationManagerImpl): AppNotificationManager
}
