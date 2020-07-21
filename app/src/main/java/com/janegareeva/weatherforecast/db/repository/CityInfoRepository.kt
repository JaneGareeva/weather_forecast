package com.janegareeva.weatherforecast.db.repository

import com.janegareeva.weatherforecast.api.ApiConfig
import com.janegareeva.weatherforecast.api.ApiService
import com.janegareeva.weatherforecast.db.model.CityInfo
import io.reactivex.Observable
import javax.inject.Inject

class CityInfoRepository @Inject constructor(val apiService: ApiService) {

    fun loadCitiesInfo(): Observable<List<CityInfo>> {
        return Observable.just(listOf(CityInfo("0","Нижний Новгород", 0.0, 5, 3.0)))
    }

    fun loadCityByNameInfo(name: String): Observable<List<CityInfo>> {
        return apiService.loadCityInfoByName(name, ApiConfig.apiKey,
            ApiConfig.celsiusMetric).map {
            listOf(CityInfo(it.id, it.name, it.main.temp, it.main.pressure, it.wind.speed))
        }
        //TODO add to db
    }

}