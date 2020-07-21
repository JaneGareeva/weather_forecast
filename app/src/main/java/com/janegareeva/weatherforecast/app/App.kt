package com.janegareeva.weatherforecast.app

import android.app.Application
import com.janegareeva.weatherforecast.di.component.AppComponent
import com.janegareeva.weatherforecast.di.component.DaggerAppComponent
import com.janegareeva.weatherforecast.di.module.ApiServiceModule
import com.janegareeva.weatherforecast.di.module.AppModule
import com.janegareeva.weatherforecast.di.module.DatabaseModule

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiServiceModule(ApiServiceModule())
            .databaseModule(DatabaseModule())
            .appModule(AppModule(this))
            .build()
    }
}