package com.janegareeva.weatherforecast.api

import com.google.gson.annotations.SerializedName

data class CityForecastListResponse(
    @SerializedName("list") val list: List<CityForecastResponse>,
    @SerializedName("city") val city: ForecastCity
)

data class CityForecastResponse(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: ForecastMain,
    @SerializedName("dt_txt") val time: String
)

data class ForecastMain(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Int
)

data class ForecastCity(
    @SerializedName("id") val cityId: String,
    @SerializedName("name") val name: String
)