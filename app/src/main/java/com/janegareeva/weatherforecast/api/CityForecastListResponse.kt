package com.janegareeva.weatherforecast.api

import com.google.gson.annotations.SerializedName

data class CityForecastListResponse(
    @SerializedName("list") val list: List<CityForecastResponse>,
    @SerializedName("city") val cityId: String
)

data class CityForecastResponse(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: ForecastMain
)

data class ForecastMain(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Int
)