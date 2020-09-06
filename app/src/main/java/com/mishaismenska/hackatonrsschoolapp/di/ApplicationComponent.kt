package com.mishaismenska.hackatonrsschoolapp.di

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.presentation.AddDrinkFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.AddUserFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.AppSettingsFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.MainFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.MapsFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.SignInFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.SplashScreenFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class, DataModule::class, DomainModule::class, RetrofitModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(addUserFragment: AddUserFragment)
    fun inject(addDrinkFragment: AddDrinkFragment)
    fun inject(splashScreenFragment: SplashScreenFragment)
    fun inject(preferences: AppSettingsFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(mapsFragment: MapsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
