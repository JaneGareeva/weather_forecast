package com.janegareeva.weatherforecast.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.janegareeva.weatherforecast.db.Config
import com.google.gson.annotations.SerializedName

@Entity(tableName = Config.CITY_INFO_TABLE)
class CityInfo(@SerializedName("name")
               @PrimaryKey
               val name: String = "") {

    @SerializedName("currentWeather")
    var currentWeather: String = ""

    @SerializedName("forecast")
    var forecast: List<String> = listOf()
}