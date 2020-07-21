package com.janegareeva.weatherforecast.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.Config

@Dao
interface CityInfoDao {

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE)
    fun loadAllCities(): List<CityInfo>

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE+" WHERE name == :name")
    fun loadCityByName(name: String): CityInfo?

    @Insert
    fun insert(cityInfo: CityInfo)
}