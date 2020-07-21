package com.janegareeva.weatherforecast.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenPresenter @Inject constructor(
    val view: MainScreenContract.MainView,
    val repository: CityInfoRepository
): MainScreenContract.Presenter, LifecycleObserver {

    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).getLifecycle().addObserver(this)
        }
    }

    override fun loadCitiesInfo() {
        view.showProgress(true)
        val disposable = repository.loadCitiesInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getCities, this::handleError)
        disposables.add(disposable)
    }

    override fun loadCityInfo(name: String) {
        val disposable = repository.loadCityByNameInfo(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getCities, this::handleError)

        disposables.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        loadCitiesInfo()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        disposables.clear()
    }

    fun getCities(citiesInfo: List<CityInfo>) {
        view.showProgress(false)
        view.showCitiesInfo(citiesInfo)
    }

    fun handleError(error: Throwable) {
        view.showProgress(false)
        view.showErrorMessage(error.localizedMessage)
    }
}