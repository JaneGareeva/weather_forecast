package com.janegareeva.weatherforecast.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun loadCityInfoByName(
        @Query("q") name: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Observable<CityInfoResponse>

    @GET("group?id={idList}")
    fun loadCitiesInfoByIdList(
        @Path("idList") idList: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Observable<List<CityInfoResponse>>
}