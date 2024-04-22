package com.example.widgets_compose

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.example.widgets_compose.widgets.SettingsDialogue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener


@SuppressLint("MissingPermission")
@Composable
fun GetFineLocation(activity: MainActivity, mFusedLocationClient : FusedLocationProviderClient, viewModel: SendMoneyViewModel) {
    if (viewModel.settingsDialogue.value!!) SettingsDialogue(
        activity = activity,
        viewModel = viewModel
    )

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val settingsClient = LocationServices.getSettingsClient(activity)
    settingsClient.checkLocationSettings(builder.build())
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Location settings are enabled, get current location
                viewModel.showSettingsDialogue(false)
                mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            viewModel.setUserLongitude(location.longitude)
                            viewModel.setUserLatitude(it.latitude)
                        }
                    }
                    .addOnFailureListener {
                    }
            } else {
                // Location settings are not enabled, show settings dialogue
                viewModel.showSettingsDialogue(true)
            }
        }

}

@SuppressLint("MissingPermission")
@Composable
fun GetCoarseLocation(activity: MainActivity, mFusedLocationClient : FusedLocationProviderClient, viewModel: SendMoneyViewModel) {
    if (viewModel.settingsDialogue.value!!) SettingsDialogue(
        activity = activity,
        viewModel = viewModel
    )

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, 1000)
        .build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val settingsClient = LocationServices.getSettingsClient(activity)
    settingsClient.checkLocationSettings(builder.build())
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Location settings are enabled, get current location
                viewModel.showSettingsDialogue(false)
                mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            viewModel.setUserLongitude(location.longitude)
                            viewModel.setUserLatitude(it.latitude)
                        }
                    }
                    .addOnFailureListener {

                    }
            } else {
                // Location settings are not enabled, show settings dialogue
                viewModel.showSettingsDialogue(true)

            }
        }

}