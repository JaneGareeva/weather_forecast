package com.janegareeva.weatherforecast.ui.base

interface BaseView {

    fun showProgress(show: Boolean)
    fun showErrorMessage(message: String)
}