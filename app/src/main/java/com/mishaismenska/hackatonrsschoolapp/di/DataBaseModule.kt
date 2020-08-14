package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.AppDataRepositoryImpl
import com.mishaismenska.hackatonrsschoolapp.data.UserStateCacheImpl
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserStateCache
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataBaseModule {
    @Binds
    @Singleton
    fun provideAppDataRepository(appDataRepositoryImpl: AppDataRepositoryImpl): AppDataRepository

    @Binds
    @Singleton
    fun provideUseStateCache(userStateCacheImpl: UserStateCacheImpl): UserStateCache
}
