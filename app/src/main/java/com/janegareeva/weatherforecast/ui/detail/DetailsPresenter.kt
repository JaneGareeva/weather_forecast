package com.janegareeva.weatherforecast.ui.detail

import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.db.model.CityForecast
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import com.janegareeva.weatherforecast.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    view: DetailScreenContract.View,
    connectivityProvider: ConnectivityProvider,
    private val cityInfoRepository: CityInfoRepository
) : BasePresenter(view, connectivityProvider), DetailScreenContract.Presenter {

    override fun loadCityInfo() {
        view.showProgress(true)
        val disposable = cityInfoRepository.loadCityByIdInfo((view as DetailScreenContract.View).cityId,
            hasInternet() )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleCityResult, this::handleError)
        disposables.add(disposable)
    }

    override fun loadForecast() {
        view.showProgress(true)
        val disposable = cityInfoRepository.loadCityForecast((view as DetailScreenContract.View).cityId,
            hasInternet() )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleForecastResult, this::handleError)
        disposables.add(disposable)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if ((state as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true) {
            loadInfo()
        }
    }

    override fun onAttach() {
        super.onAttach()
        if (!hasInternet()) {
            loadInfo()
        }
    }

    private fun loadInfo() {
        loadCityInfo()
        loadForecast()
    }

    private fun handleForecastResult(cityForecast: List<CityForecast>) {
        view.showProgress(false)
        (view as DetailScreenContract.View).showForecast(cityForecast)
    }

    private fun handleCityResult(city: CityInfo) {
        view.showProgress(false)
        (view as DetailScreenContract.View).showCityInfo(city)
    }

    private fun handleError(error: Throwable) {
        view.showProgress(false)
        error.localizedMessage?.let {
            view.showErrorMessage(it)
        }
    }
}