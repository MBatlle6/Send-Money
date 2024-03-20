package com.example.widgets_compose

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class NetworkStateMonitor(private val context: Context, viewModel: SendMoneyViewModel) : ConnectivityManager.NetworkCallback() {

    private lateinit var noNetworkDialog: AlertDialog

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, this)

        // check the network state when the class is initialized
        val network = connectivityManager.activeNetwork
        if (network == null || !connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)!!
        ) {
            showNoNetworkDialog()
        }
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        dismissNoNetworkDialog()

    }

    override fun onLost(network: Network) {
        super.onLost(network)
        showNoNetworkDialog()
    }

    private fun showNoNetworkDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.pleaseActivateConnection))
            .setTitle(context.getString(R.string.noNetworkConnection))

        noNetworkDialog = builder.create()
        noNetworkDialog.setCancelable(false)
        noNetworkDialog.show()
    }

    private fun dismissNoNetworkDialog() {
        if (::noNetworkDialog.isInitialized) {
            noNetworkDialog.dismiss()
        }
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }
}
