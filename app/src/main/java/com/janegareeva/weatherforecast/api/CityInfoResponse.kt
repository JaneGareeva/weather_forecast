package com.janegareeva.weatherforecast.api

import com.google.gson.annotations.SerializedName

data class CityInfoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("main") val main: MainWeatherInfo,
    @SerializedName("wind") val wind: WindInfo,
    @SerializedName("weather") val weather: List<WeatherInfo>
) {

    data class MainWeatherInfo(
        @SerializedName("temp") val temp: Double,
        @SerializedName("pressure") val pressure: Int
    )

    data class WindInfo(@SerializedName("speed") val speed: Double)

    data class WeatherInfo(
        @SerializedName("main") val main: String,
        @SerializedName("description") val description: String
    )
}

