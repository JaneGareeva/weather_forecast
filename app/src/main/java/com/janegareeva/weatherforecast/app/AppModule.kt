package com.janegareeva.weatherforecast.app

import android.app.Application
import android.content.Context
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Application) {
    private val context: Context

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideCityInfoRepository(): CityInfoRepository {
        return CityInfoRepository()
    }

    init {
        this.context = context
    }
}