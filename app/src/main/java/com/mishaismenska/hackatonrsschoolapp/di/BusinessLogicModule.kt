package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.logic.UserInputValidatingManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface BusinessLogicModule {
    @Binds
    fun provideUserInputValidatingManager(userInputValidationManagerImpl: UserInputValidatingManagerImpl): UserInputValidatingManager
}
