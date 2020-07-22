package com.janegareeva.weatherforecast.api.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import javax.inject.Inject

class ConnectivityProviderImpl @Inject constructor(private val cm: ConnectivityManager) : ConnectivityProvider {

    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<ConnectivityProvider.ConnectivityStateListener>()
    private var subscribed = false

    private val networkCallback = ConnectivityCallback()

    private fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    private fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            ConnectivityProvider.NetworkState.ConnectedState.Connected(capabilities)
        } else {
            ConnectivityProvider.NetworkState.NotConnectedState
        }
    }

    fun dispatchChange(state: ConnectivityProvider.NetworkState) {
        handler.post {
            for (listener in listeners) {
                listener.onStateChange(state)
            }
        }
    }

    override fun addListener(listener: ConnectivityProvider.ConnectivityStateListener) {
        listeners.add(listener)
        listener.onStateChange(getNetworkState())
        verifySubscription()
    }

    override fun removeListener(listener: ConnectivityProvider.ConnectivityStateListener) {
        listeners.remove(listener)
        verifySubscription()
    }

    private fun verifySubscription() {
        if (!subscribed && listeners.isNotEmpty()) {
            subscribe()
            subscribed = true
        } else if (subscribed && listeners.isEmpty()) {
            unsubscribe()
            subscribed = false
        }
    }

    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(ConnectivityProvider.NetworkState.ConnectedState.Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(ConnectivityProvider.NetworkState.NotConnectedState)
        }
    }
}