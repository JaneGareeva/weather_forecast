package com.janegareeva.weatherforecast.ui.main

import androidx.lifecycle.*
import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenPresenter @Inject constructor(
    val view: MainScreenContract.MainView,
    val repository: CityInfoRepository,
    val connectivityProvider: ConnectivityProvider
): MainScreenContract.Presenter, LifecycleObserver, ConnectivityProvider.ConnectivityStateListener {

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun loadCitiesInfo() {
        view.showProgress(true)
        val disposable = repository.loadCitiesInfo(connectivityProvider.getNetworkState().hasInternet())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleCitiesResult, this::handleError)
        disposables.add(disposable)
    }

    override fun loadCityInfo(name: String) {
        val disposable = repository.loadCityByNameInfo(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleCitiesResult, this::handleError)

        disposables.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        connectivityProvider.addListener(this)
        loadCitiesInfo()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        connectivityProvider.removeListener(this)
        disposables.clear()
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if ((state as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true) {
            loadCitiesInfo()
        }
    }

    private fun handleCitiesResult(citiesInfo: List<CityInfo>) {
        view.showProgress(false)
        view.showCitiesInfo(citiesInfo)
    }

    private fun handleError(error: Throwable) {
        view.showProgress(false)
        error.localizedMessage?.let {
            view.showErrorMessage(it)
        }
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }
}