package com.mishaismenska.hackatonrsschoolapp.di

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.ui.AddDrinkFragment
import com.mishaismenska.hackatonrsschoolapp.ui.AddUserFragment
import com.mishaismenska.hackatonrsschoolapp.ui.MainFragment
import com.mishaismenska.hackatonrsschoolapp.ui.SplashScreenFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class, BusinessLogicModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(addUserFragment: AddUserFragment)
    fun inject(addDrinkFragment: AddDrinkFragment)
    fun inject(splashScreenFragment: SplashScreenFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
