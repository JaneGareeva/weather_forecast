package com.janegareeva.weatherforecast.di.component

import com.janegareeva.weatherforecast.app.AppModule
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =[AppModule::class])
interface AppComponent {

    fun provideCityInfoRepository(): CityInfoRepository
}