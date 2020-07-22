package com.janegareeva.weatherforecast.ui.main

import androidx.lifecycle.*
import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import com.janegareeva.weatherforecast.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenPresenter @Inject constructor(
    view: MainScreenContract.MainView,
    val repository: CityInfoRepository,
    connectivityProvider: ConnectivityProvider
): BasePresenter(view, connectivityProvider), MainScreenContract.Presenter {

    override fun loadCitiesInfo() {
        view.showProgress(true)
        val disposable = repository.loadCitiesInfo(hasInternet())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleCitiesResult, this::handleError)
        disposables.add(disposable)
    }

    override fun loadCityInfo(name: String) {
        val disposable = repository.findAndAddCityByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleCitiesResult, this::handleError)

        disposables.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        super.onAttach()
       // loadCitiesInfo()
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if ((state as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true) {
            loadCitiesInfo()
        }
    }

    private fun handleCitiesResult(citiesInfo: List<CityInfo>) {
        view.showProgress(false)
        (view as MainScreenContract.MainView).showCitiesInfo(citiesInfo)
    }

    private fun handleError(error: Throwable) {
        view.showProgress(false)
        error.localizedMessage?.let {
            view.showErrorMessage(it)
        }
    }
}