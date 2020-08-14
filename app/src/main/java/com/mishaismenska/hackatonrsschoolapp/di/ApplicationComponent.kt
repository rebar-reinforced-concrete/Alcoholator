package com.mishaismenska.hackatonrsschoolapp.di

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.presentation.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [BusinessLogicModule::class, DataBaseModule::class, DomainModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(addUserFragment: AddUserFragment)
    fun inject(addDrinkFragment: AddDrinkFragment)
    fun inject(splashScreenFragment: SplashScreenFragment)
    fun inject(preferences: AppSettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
