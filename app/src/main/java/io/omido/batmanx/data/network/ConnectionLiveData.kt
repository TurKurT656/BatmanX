package io.omido.batmanx.data.network

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import io.omido.batmanx.util.ktx.logD

/**
 * A singleton to contain the Connectivity status of application.
 * It provides the status as a live data to be observed inside the view by ConnectionLiveData.observe
 */
object ConnectionLiveData : LiveData<Boolean>() {

    fun init(context: Context) {
        appContext = context.applicationContext
        connectivityManager =
            appContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isConnected(): Boolean = value ?: false

    private lateinit var appContext: Context

    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                getConnectivityManagerCallback()
            )
            else -> preNougatNetworkAvailableRequest()

        }
    }

    override fun onInactive() {
        super.onInactive()
        try {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        } catch (e: Exception) {
            logD(e.localizedMessage?.toString() ?: "")
        }
    }

    private fun preNougatNetworkAvailableRequest() {
        val builder = NetworkRequest.Builder()
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(
            builder.build(),
            getConnectivityManagerCallback()
        )
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                postValue(true)
            }

            override fun onLost(network: Network?) {
                postValue(false)
            }
        }.also { connectivityManagerCallback = it }
    }
}