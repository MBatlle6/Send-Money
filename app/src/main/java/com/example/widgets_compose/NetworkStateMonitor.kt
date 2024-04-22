package com.example.widgets_compose

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlin.system.exitProcess

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
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            if (!viewModel.allowAllConnections.value!!){ //Linkar amb les shared preferences
                showSharedPreferencesDialog()
            }
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        showNoNetworkDialog()
    }

    private fun showNoNetworkDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.pleaseActivateConnection))
            .setTitle(context.getString(R.string.noNetworkConnection))
            .setPositiveButton(R.string.restartApp) { _: DialogInterface, _: Int ->
                val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
                exitProcess(0)
            }
            .setCancelable(false)

        noNetworkDialog = builder.create()
        noNetworkDialog.show()
    }

    private fun showSharedPreferencesDialog() {
        val builder = AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.wrongSharedPreferences))
            .setPositiveButton(R.string.restartApp) { _: DialogInterface, _: Int ->
                val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
                exitProcess(0)
            }
            .setCancelable(false)

        sharedPreferencesDialog = builder.create()
        sharedPreferencesDialog.show()
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
