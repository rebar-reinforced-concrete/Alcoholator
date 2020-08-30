package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.*
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.UserStateRepository
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
    fun provideUseStateCache(userStateCacheImpl: UserStateRepositoryImpl): UserStateRepository

    @Binds
    @Singleton
    fun provideGendersProvider(gendersProviderImpl: GendersRepositoryImpl): GendersRepository

    @Binds
    @Singleton
    fun provideDrinkTypeProvider(drinkTypeProvider: DrinkTypeRepositoryImpl): DrinkTypeRepository

    @Binds
    @Singleton
    fun provideNetworkManager(networkManagerImpl: NetworkManagerImpl): NetworkManager

    @Binds
    @Singleton
    fun providePermissionManager(permissionManagerImpl: PermissionManagerImpl): PermissionManager
}
