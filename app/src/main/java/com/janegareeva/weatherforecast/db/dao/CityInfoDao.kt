package com.janegareeva.weatherforecast.db.dao

import androidx.room.Insert
import androidx.room.Query
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.Config

interface CityInfoDao {

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE)
    fun loadAllCities()

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE+" WHERE name == :name")
    fun loadCityByName(name: String)

    @Insert
    fun insert(cityInfo: CityInfo)

}