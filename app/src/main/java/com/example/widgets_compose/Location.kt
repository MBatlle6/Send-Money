package com.example.widgets_compose

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.Composable
import com.example.widgets_compose.widgets.SettingsDialogue
import com.google.android.gms.location.FusedLocationProviderClient
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

    mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
        override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

        override fun isCancellationRequested() = false
    })
        .addOnSuccessListener { location: Location? ->
            viewModel.setUserLongitude(location!!.longitude)
            viewModel.setUserLatitude(location.latitude)
        }
        .addOnFailureListener {
            viewModel.showSettingsDialogue(true)
        }

}

@SuppressLint("MissingPermission")
@Composable
fun GetCoarseLocation(activity: MainActivity, mFusedLocationClient : FusedLocationProviderClient, viewModel: SendMoneyViewModel) {
    if (viewModel.settingsDialogue.value!!) SettingsDialogue(
        activity = activity,
        viewModel = viewModel
    )

    mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, object : CancellationToken() {
        override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

        override fun isCancellationRequested() = false
    })
        .addOnSuccessListener { location: Location? ->
            viewModel.setUserLongitude(location!!.longitude)
            viewModel.setUserLatitude(location.latitude)
        }
        .addOnFailureListener {
            viewModel.showSettingsDialogue(true)
        }

}