package com.janegareeva.weatherforecast.ui.main

import com.janegareeva.weatherforecast.db.model.CityInfo
import java.lang.Exception


class MainScreenContract {

    interface MainView {
        fun showCitiesInfo(cities: List<CityInfo>)
        fun showProgress(show: Boolean)
        fun showErrorMessage(message: String)
    }

    interface Presenter {
        fun loadCitiesInfo()
    }
}