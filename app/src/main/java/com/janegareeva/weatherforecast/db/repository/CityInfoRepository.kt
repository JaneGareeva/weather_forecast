package com.janegareeva.weatherforecast.db.repository

import com.janegareeva.weatherforecast.api.ApiConfig
import com.janegareeva.weatherforecast.api.ApiService
import com.janegareeva.weatherforecast.api.CityInfoListResponse
import com.janegareeva.weatherforecast.api.CityInfoResponse
import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.db.Config
import com.janegareeva.weatherforecast.db.dao.CityInfoDao
import com.janegareeva.weatherforecast.db.model.CityInfo
import io.reactivex.Single
import javax.inject.Inject

class CityInfoRepository @Inject constructor(
    val apiService: ApiService,
    val cityInfoDao: CityInfoDao
) {

    fun loadCitiesInfo(hasInternet: Boolean = true): Single<List<CityInfo>> {
        return if (hasInternet) {
            loadRemoteCitiesInfo()
        } else {
            getCitiesFromDb()
        }
    }

    private fun loadRemoteCitiesInfo(): Single<List<CityInfo>> {
        return getCitiesFromDb().map {
            getCityIdListWithDefault(it)
        }.flatMap { idList ->
            apiService.loadCitiesInfoByIdList(
                idList.joinToString(","),
                ApiConfig.apiKey, ApiConfig.celsiusMetric
            )
        }.map {
            transform(it)
        }.doAfterSuccess {
            saveToDb(it)
        }
    }

    fun loadCityByNameInfo(name: String): Single<List<CityInfo>> {
        return apiService.loadCityInfoByName(
            name, ApiConfig.apiKey,
            ApiConfig.celsiusMetric
        )
            .flatMap {
                saveToDb(listOf(transform(it)))
                getCitiesFromDb()
            }
    }

    private fun saveToDb(cities: List<CityInfo>) {
        cityInfoDao.insert(cities)
    }

    private fun getCitiesFromDb(): Single<List<CityInfo>> {
        return cityInfoDao.loadAllCities()
    }

    private fun transform(cityInfoResponse: CityInfoResponse): CityInfo =
        CityInfo(
            cityInfoResponse.id, cityInfoResponse.name, cityInfoResponse.main.temp,
            cityInfoResponse.main.pressure, cityInfoResponse.wind.speed
        )

    private fun transform(cities: CityInfoListResponse): List<CityInfo> =
        cities.list.map { transform(it) }

    private fun getCityIdListWithDefault(cities: List<CityInfo>): List<String> =
        (cities.map { it -> it.id } + Config.defaultCities).distinct()
}