package com.janegareeva.weatherforecast.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.janegareeva.weatherforecast.db.Config
import com.janegareeva.weatherforecast.db.model.CityForecast
import io.reactivex.Single

@Dao
interface CityForecastDao {

   @Query("SELECT * FROM "+ Config.FORECAST_TABLE+" WHERE cityId=:cityId")
    fun loadForecast(cityId: String): Single<List<CityForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: List<CityForecast>)
}