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

 class NetworkStateMonitor(private val context: Context, private val viewModel: SendMoneyViewModel) : ConnectivityManager.NetworkCallback() {

    private lateinit var noNetworkDialog: AlertDialog
    private lateinit var sharedPreferencesDialog: AlertDialog




    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
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

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Toast.makeText(context,context.getString(R.string.wifiConnected),Toast.LENGTH_LONG).show()
            //dismissSharedPreferencesDialog()
        }
        else{
            Toast.makeText(context,context.getString(R.string.mobileConnected),Toast.LENGTH_LONG).show()
            if (!viewModel.allowAllConnections.value!!){ //Linkar amb les shared preferences
                Toast.makeText(context,context.getString(R.string.wrongSharedPreferences),Toast.LENGTH_LONG).show()
                //showSharedPreferencesDialog()
            }
        }
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        dismissNoNetworkDialog()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Toast.makeText(context,context.getString(R.string.noNetworkConnection),Toast.LENGTH_LONG).show()
        //showNoNetworkDialog()
    }

    private fun showNoNetworkDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.pleaseActivateConnection))
            .setTitle(context.getString(R.string.noNetworkConnection))

        noNetworkDialog = builder.create()
        noNetworkDialog.setCancelable(false)
        noNetworkDialog.show()
    }

    private fun showSharedPreferencesDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.wrongSharedPreferences))

        sharedPreferencesDialog = builder.create()
        sharedPreferencesDialog.setCancelable(false)
        sharedPreferencesDialog.show()
    }

    private fun dismissNoNetworkDialog() {
        if (::noNetworkDialog.isInitialized) {
            noNetworkDialog.dismiss()
        }
    }

    private fun dismissSharedPreferencesDialog() {
        if (::sharedPreferencesDialog.isInitialized) {
            sharedPreferencesDialog.dismiss()
        }
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }
}
