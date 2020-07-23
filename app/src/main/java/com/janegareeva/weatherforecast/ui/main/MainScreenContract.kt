package com.janegareeva.weatherforecast.ui.main

import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.ui.base.BaseView

class MainScreenContract {

    interface MainView : BaseView {
        fun showCitiesInfo(cities: List<CityInfo>)
        fun showAddCityEnabled(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadCitiesInfo()
        fun loadCityInfo(name: String)
    }
}