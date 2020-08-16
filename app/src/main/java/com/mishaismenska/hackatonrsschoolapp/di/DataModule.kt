package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.AppDataRepositoryImpl
import com.mishaismenska.hackatonrsschoolapp.data.GendersProviderImpl
import com.mishaismenska.hackatonrsschoolapp.data.UserStateCacheImpl
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.data.interfaces.UserStateCache
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {
    @Binds
    @Singleton
    fun provideAppDataRepository(appDataRepositoryImpl: AppDataRepositoryImpl): AppDataRepository

    @Binds
    @Singleton
    fun provideUseStateCache(userStateCacheImpl: UserStateCacheImpl): UserStateCache

    @Binds
    @Singleton
    fun provideGendersProvider(gendersProviderImpl: GendersProviderImpl): GendersProvider
}
