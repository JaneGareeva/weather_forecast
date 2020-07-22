package com.janegareeva.weatherforecast.di.module

import android.content.Context
import androidx.room.Room
import com.janegareeva.weatherforecast.db.Config
import com.janegareeva.weatherforecast.db.WeatherForecastDb
import com.janegareeva.weatherforecast.db.dao.CityForecastDao
import com.janegareeva.weatherforecast.db.dao.CityInfoDao
import com.janegareeva.weatherforecast.ui.base.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DatabaseModule {
    @Provides
    @Named(DATABASE)
    fun provideDatabaseName(): String {
        return Config.DATABASE_NAME
    }

    @Provides
    @AppScope
    fun provideDb(
        context: Context,
        @Named(DATABASE) databaseName: String
    ): WeatherForecastDb {
        return Room.databaseBuilder(context, WeatherForecastDb::class.java, databaseName).build()
    }

    @Provides
    @AppScope
    fun provideDao(db: WeatherForecastDb): CityInfoDao {
        return db.cityInfoDao()
    }

    @Provides
    @AppScope
    fun provideForecastDao(db: WeatherForecastDb): CityForecastDao {
        return db.cityForecastDao()
    }

    companion object {
        private const val DATABASE = "database_name"
    }
}