package com.janegareeva.weatherforecast.db.repository

import com.janegareeva.weatherforecast.api.*
import com.janegareeva.weatherforecast.db.Config
import com.janegareeva.weatherforecast.db.dao.CityForecastDao
import com.janegareeva.weatherforecast.db.dao.CityInfoDao
import com.janegareeva.weatherforecast.db.model.CityForecast
import com.janegareeva.weatherforecast.db.model.CityInfo
import io.reactivex.Single
import javax.inject.Inject

class CityInfoRepository @Inject constructor(
    val apiService: ApiService,
    val cityInfoDao: CityInfoDao,
    val cityForecastDao: CityForecastDao
) {

    fun loadCitiesInfo(hasInternet: Boolean = true): Single<List<CityInfo>> {
        return if (hasInternet) {
            loadRemoteCitiesInfo()
        } else {
            getCitiesFromDb()
        }
    }

    fun findAndAddCityByName(name: String): Single<List<CityInfo>> {
        return apiService.loadCityInfoByName(
            name, ApiConfig.apiKey,
            ApiConfig.celsiusMetric
        )
            .flatMap {
                saveToDb(listOf(transform(it)))
                getCitiesFromDb()
            }
    }

    fun loadCityByIdInfo(cityId: String, hasInternet: Boolean = true): Single<CityInfo> {
        return if (hasInternet) {
            apiService.loadCityInfoById(cityId, ApiConfig.apiKey, ApiConfig.celsiusMetric)
                .map {
                    transform(it)
                }
        } else {
            Single.just(cityInfoDao.loadCityById(cityId) ?: CityInfo(""))
        }
    }

    fun loadCityForecast(cityId: String, hasInternet: Boolean = true): Single<List<CityForecast>> {
        return if (hasInternet) {
            loadRemoteCityForecast(cityId)
        } else {
            loadLocalCityForecast(cityId)
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

    private fun loadRemoteCityForecast(cityId: String): Single<List<CityForecast>> {
        return apiService.loadForecastForCity(cityId, ApiConfig.apiKey, ApiConfig.celsiusMetric).map {
            transform(it)
        }.doAfterSuccess {
            saveForecastToDb(it)
        }
    }

    private fun loadLocalCityForecast(cityId: String): Single<List<CityForecast>> {
        return cityForecastDao.loadForecast(cityId)
    }

    private fun saveToDb(cities: List<CityInfo>) {
        cityInfoDao.insert(cities)
    }

    private fun saveForecastToDb(forecast: List<CityForecast>) {
        cityForecastDao.insert(forecast)
    }

    private fun getCitiesFromDb(): Single<List<CityInfo>> {
        return cityInfoDao.loadAllCities()
    }

    private fun transform(cityInfoResponse: CityInfoResponse): CityInfo =
        CityInfo(
            cityInfoResponse.id, cityInfoResponse.name, cityInfoResponse.main.temp,
            cityInfoResponse.main.pressure, cityInfoResponse.wind.speed, cityInfoResponse.weather.firstOrNull()?.description ?: ""
        )

    private fun transform(cities: CityInfoListResponse): List<CityInfo> =
        cities.list.map { transform(it) }

    private fun transform(forecastListResponse: CityForecastListResponse): List<CityForecast> {
        val cityId = forecastListResponse.cityId
        return forecastListResponse.list.map {
            CityForecast(cityId, it.dt, it.main.temp, it.main.pressure)
        }
    }

    private fun getCityIdListWithDefault(cities: List<CityInfo>): List<String> =
        (cities.map { it -> it.id } + Config.defaultCities).distinct()
}