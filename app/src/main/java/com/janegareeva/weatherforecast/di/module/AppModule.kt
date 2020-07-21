package com.janegareeva.weatherforecast.di.module

import android.app.Application
import android.content.Context
import com.janegareeva.weatherforecast.api.ApiService
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import com.janegareeva.weatherforecast.ui.base.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (includes = [ApiServiceModule::class, DatabaseModule::class])
class AppModule(context: Application) {
    private val context: Context

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }

    @Provides
    @AppScope
    fun provideCityInfoRepository(apiService: ApiService): CityInfoRepository {
        return CityInfoRepository(apiService)
    }

    init {
        this.context = context
    }
}