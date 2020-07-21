package com.janegareeva.weatherforecast.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.janegareeva.weatherforecast.db.Config
import com.google.gson.annotations.SerializedName

@Entity(tableName = Config.CITY_INFO_TABLE)
data class CityInfo(
    @SerializedName("id") @PrimaryKey val id: String,
    @SerializedName("name") val name: String = "",
    @SerializedName("temp") val temp: Double = 0.0,
    @SerializedName("pressure") val pressure: Int = 0,
    @SerializedName("wind") var wind: Double = 0.0)