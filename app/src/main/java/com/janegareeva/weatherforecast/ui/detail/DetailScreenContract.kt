package com.janegareeva.weatherforecast.ui.detail

import com.janegareeva.weatherforecast.db.model.CityForecast
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.ui.base.BaseView

class DetailScreenContract {

    interface View: BaseView {
        val cityId: String
        fun showCityInfo(city: CityInfo)
        fun showForecast(cityForecast: List<CityForecast>)
    }

    interface Presenter {
        fun loadCityInfo()
        fun loadForecast()
    }
}