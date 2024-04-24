package com.example.widgets_compose.screens.app

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.example.widgets_compose.R

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData
import com.example.widgets_compose.ui.theme.Turquoise
import com.example.widgets_compose.widgets.OtherUserEmailWritingButton
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
    if(viewModel.currentLocation.value!!) {
        UserCurrentLocation(viewModel = viewModel, activity = activity)
        return
    }
    if(viewModel.previousLocation.value!!) {
        UserPreviousLocation(viewModel = viewModel, activity = activity)
        return
    }
    if(viewModel.otherUserLocation.value!!) {
        OtherUserLocation(viewModel = viewModel, activity = activity)
        return
    }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Localized description",
                modifier = Modifier.size(32.dp)
            )
            OtherUserEmailWritingButton(label = activity.getString(R.string.otherUserEmail) , viewModel = viewModel)
            Button(
                onClick = {
                    viewModel.showOtherUserLocation(true)
                    viewModel.setOtherUserEmail("")
                          },
                colors = ButtonDefaults.buttonColors(Turquoise)
            )
            {
                Text(text = activity.getString(R.string.getOtherUserLocation))
            }
            Spacer(modifier = Modifier.height(72.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.showCurrentLocation(true) },
                    colors = ButtonDefaults.buttonColors(Turquoise)
                    ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(text = activity.getString(R.string.currentLocation))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.showPreviousLocation(true) },
                    //colors = ButtonDefaults.buttonColors(Color.Black)

                    ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(text = activity.getString(R.string.previousLocation))
                }

            }
        }
    }
}


//Make UserPreviousLocation() retrieve the previus location of the user, stored on the Shared Preferences
@Composable
fun UserPreviousLocation(viewModel: SendMoneyViewModel, activity: MainActivity){
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





@Composable
fun UserCurrentLocation(viewModel: SendMoneyViewModel, activity: MainActivity){
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

//Make OtherUserLocation() retrieve the location of the email's user, stored on Firebase

@Composable
fun OtherUserLocation(viewModel: SendMoneyViewModel, activity: MainActivity){
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