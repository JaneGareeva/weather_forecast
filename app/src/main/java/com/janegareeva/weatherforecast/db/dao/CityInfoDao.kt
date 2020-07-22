package com.janegareeva.weatherforecast.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.Config
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CityInfoDao {

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE)
    fun loadAllCities(): Single<List<CityInfo>>

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE+" WHERE name == :name")
    fun loadCityByName(name: String): Single<CityInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityInfoList: List<CityInfo>)
}