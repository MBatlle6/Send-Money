package com.example.widgets_compose.screens.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import com.example.widgets_compose.R

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
) {
    if(viewModel.userLatitude.value == 0.0 || viewModel.userLongitude.value == 0.0){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Localized description",
                modifier = Modifier.size(32.dp)
            )
        }
    }
    else{
        val myLocation = LatLng(viewModel.userLatitude.value!!, viewModel.userLongitude.value!!)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(myLocation, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
        ) {
            Marker(
                state = MarkerState(position = myLocation),
                title = activity.getString(R.string.myLocation),
            )
        }
    }
}