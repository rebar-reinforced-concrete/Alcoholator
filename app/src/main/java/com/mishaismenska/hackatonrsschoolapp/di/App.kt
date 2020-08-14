package com.mishaismenska.hackatonrsschoolapp.di

import android.app.Application
import com.mishaismenska.hackatonrsschoolapp.di.DaggerApplicationComponent

class App : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}
