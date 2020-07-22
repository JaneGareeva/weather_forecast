package com.janegareeva.weatherforecast.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun loadCityInfoByName(
        @Query("q") name: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityInfoResponse>

    @GET("group")
    fun loadCitiesInfoByIdList(
        @Query("id") idList: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Single<CityInfoListResponse>
}