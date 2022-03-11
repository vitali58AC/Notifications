package com.example.notifications.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log


class NetworkMonitoringUtil(context: Context) : NetworkCallback() {

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        //.addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
//        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
  //      .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //context.getSystemService(ConnectivityManager::class.java)

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d("Network_monitor", "onAvailable() called: Connected to network")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.e("Network_monitor", "onLost() called: Lost network connection")
    }

    /**
     * Registers the Network-Request callback
     * (Note: Register only once to prevent duplicate callbacks)
     */
    fun registerNetworkCallbackEvents() {
        Log.d("Network_monitor", "registerNetworkCallbackEvents() called")
        connectivityManager.registerNetworkCallback(networkRequest, this)
        //connectivityManager.requestNetwork(networkRequest, this)
    }

}
