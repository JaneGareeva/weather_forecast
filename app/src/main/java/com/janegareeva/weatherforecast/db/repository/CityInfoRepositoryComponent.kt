package com.janegareeva.weatherforecast.db.repository

import com.janegareeva.weatherforecast.app.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =[AppModule::class])
interface CityInfoRepositoryComponent {
    fun provideCityInfoRepository(): CityInfoRepository
}