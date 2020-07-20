package com.janegareeva.weatherforecast.app

import android.app.Application
import com.janegareeva.weatherforecast.di.component.AppComponent
import com.janegareeva.weatherforecast.di.component.DaggerAppComponent

class App: Application() {

    lateinit var injector: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        injector = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }
}