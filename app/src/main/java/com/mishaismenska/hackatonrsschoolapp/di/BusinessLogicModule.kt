package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.presentation.AppNotificationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.domain.logic.CalculationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.presentation.UserInputValidatingManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface BusinessLogicModule {
    @Binds
    @Singleton
    fun provideUserInputValidatingManager(userInputValidationManagerImpl: UserInputValidatingManagerImpl): UserInputValidatingManager

    @Binds
    @Singleton
    fun provideCalculationsManager(calculationManager: CalculationManagerImpl): CalculationManager

    @Binds
    @Singleton
    fun provideAppNotificationManager(appNotificationManagerImpl: AppNotificationManagerImpl): AppNotificationManager
}
