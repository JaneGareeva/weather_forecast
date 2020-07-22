package com.janegareeva.weatherforecast.di.component

import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.di.module.AppModule
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import com.janegareeva.weatherforecast.ui.base.AppScope
import dagger.Component

@AppScope
@Component(modules =[AppModule::class])
interface AppComponent {

    fun provideCityInfoRepository(): CityInfoRepository
    fun provideConnectivityProvider(): ConnectivityProvider
}