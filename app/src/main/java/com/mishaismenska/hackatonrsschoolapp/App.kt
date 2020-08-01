package com.mishaismenska.hackatonrsschoolapp

import android.app.Application
import com.mishaismenska.hackatonrsschoolapp.di.AppComponent
import com.mishaismenska.hackatonrsschoolapp.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
