package com.janegareeva.weatherforecast.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun loadCityInfoByName(
        @Query("q") name: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityInfoResponse>

    @GET("weather")
    fun loadCityInfoById(
        @Query("id") id: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityInfoResponse>

    @GET("group")
    fun loadCitiesInfoByIdList(
        @Query("id") idList: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityInfoListResponse>

    @GET("forecast")
    fun loadForecastForCity(
        @Query("id") id: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityForecastListResponse>
}