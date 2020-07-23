package com.janegareeva.weatherforecast.di.module

import android.app.Application
import android.content.Context
import com.janegareeva.weatherforecast.api.ApiService
import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.db.dao.CityForecastDao
import com.janegareeva.weatherforecast.db.dao.CityInfoDao
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import com.janegareeva.weatherforecast.ui.base.AppScope
import dagger.Module
import dagger.Provides

@Module(includes = [ApiServiceModule::class, DatabaseModule::class])
class AppModule(context: Application) {
    private val context: Context

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }

    @Provides
    @AppScope
    fun provideConnectivityProvider(): ConnectivityProvider {
        return ConnectivityProvider.createProvider(context)
    }

    @Provides
    @AppScope
    fun provideCityInfoRepository(
        apiService: ApiService,
        cityInfoDao: CityInfoDao,
        cityForecastDao: CityForecastDao
    ): CityInfoRepository {
        return CityInfoRepository(apiService, cityInfoDao, cityForecastDao)
    }

    init {
        this.context = context
    }
}