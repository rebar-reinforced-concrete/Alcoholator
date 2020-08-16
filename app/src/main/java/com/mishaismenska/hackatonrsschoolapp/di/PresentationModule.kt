package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.presentation.AppNotificationManagerImpl
import com.mishaismenska.hackatonrsschoolapp.presentation.UserInputValidatingManagerImpl
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.UserInputValidatingManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface PresentationModule {
    @Binds
    @Singleton
    fun provideUserInputValidatingManager(userInputValidationManagerImpl: UserInputValidatingManagerImpl): UserInputValidatingManager

    @Binds
    @Singleton
    fun provideAppNotificationManager(appNotificationManagerImpl: AppNotificationManagerImpl): AppNotificationManager
}
