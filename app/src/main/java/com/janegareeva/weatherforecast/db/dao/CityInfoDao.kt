package com.janegareeva.weatherforecast.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.Config
import io.reactivex.Single

@Dao
interface CityInfoDao {

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE)
    fun loadAllCities(): Single<List<CityInfo>>

    @Query("SELECT * FROM "+ Config.CITY_INFO_TABLE+" WHERE id =:id")
    fun loadCityById(id: String): Single<CityInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityInfoList: List<CityInfo>)
}