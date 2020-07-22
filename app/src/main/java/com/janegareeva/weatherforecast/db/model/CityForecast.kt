package com.janegareeva.weatherforecast.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.janegareeva.weatherforecast.db.Config

@Entity(
    tableName = Config.FORECAST_TABLE, foreignKeys = [ForeignKey(
        entity = CityInfo::class,
        parentColumns = ["id"], childColumns = ["cityId"], onDelete = CASCADE
    )],
    primaryKeys = ["cityId", "dt"]
)
class CityForecast(
    @SerializedName("cityId") val cityId: String,
    @SerializedName("dt") val dt: Long = 0L,
    @SerializedName("temp") val temp: Double = 0.0,
    @SerializedName("pressure") val pressure: Int = 0
)