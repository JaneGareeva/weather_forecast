package com.janegareeva.weatherforecast.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.janegareeva.weatherforecast.db.dao.CityInfoDao
import com.janegareeva.weatherforecast.db.model.CityInfo

@Database(entities = [CityInfo::class],version = 1)
abstract class WeatherForecastDb: RoomDatabase() {

    abstract fun cityInfoDao(): CityInfoDao
}