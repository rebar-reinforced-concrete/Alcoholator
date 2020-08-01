package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.AppDataRepositoryImpl
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun provideAppDataRepository(appDataRepositoryImpl: AppDataRepositoryImpl): AppDataRepository
}
