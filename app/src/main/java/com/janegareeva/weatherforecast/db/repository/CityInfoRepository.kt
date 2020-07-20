package com.janegareeva.weatherforecast.db.repository

import com.janegareeva.weatherforecast.db.model.CityInfo
import io.reactivex.Observable
import javax.inject.Inject

class CityInfoRepository @Inject constructor() {

    fun loadCitiesInfo(): Observable<List<CityInfo>> {
        return Observable.just(listOf(CityInfo("Нижний Новгород").apply {
            this.currentWeather = "пасмурно, +18"
        }))
    }
}