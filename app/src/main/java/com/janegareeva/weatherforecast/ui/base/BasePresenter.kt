package com.janegareeva.weatherforecast.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.janegareeva.weatherforecast.api.connectivity.ConnectivityProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter (
    val view: BaseView,
    val connectivityProvider: ConnectivityProvider
) : LifecycleObserver, ConnectivityProvider.ConnectivityStateListener {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    init {
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    abstract override fun onStateChange(state: ConnectivityProvider.NetworkState)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onAttach() {
        connectivityProvider.addListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        connectivityProvider.removeListener(this)
        disposables.clear()
    }

    protected fun hasInternet(): Boolean {
        return (connectivityProvider.getNetworkState() as?
                ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet ?: false
    }
}