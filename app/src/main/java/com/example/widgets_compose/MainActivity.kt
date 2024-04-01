package com.example.widgets_compose

import SendMoneyViewModelFactory
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.widgets_compose.screens.BaseScreen
import com.example.widgets_compose.ui.theme.Widgets_ComposeTheme
import com.example.widgets_compose.widgets.ConfigSnackbar
import com.example.widgets_compose.widgets.OkSnackbar
import com.example.widgets_compose.widgets.SettingsDialogue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.LiveData
import java.util.Locale


class MainActivity : ComponentActivity() {


    private lateinit var networkStateMonitor: NetworkStateMonitor
    private lateinit var networkState: LiveData<Boolean>
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    lateinit var locationManager : LocationManager
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>




    private val sharedPreferencesData by lazy {
        SharedPreferencesData.getInstance(this)
    }

    private val viewModel: SendMoneyViewModel by viewModels {
        SendMoneyViewModelFactory(sharedPreferencesData)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true){  //Gestionar cuanda el user hace return
        override fun handleOnBackPressed() {
            backAction(viewModel)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        //calculateTokens(viewModel, this)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            when {
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ->{
                    viewModel.startApp()
                }
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ->{
                    viewModel.startApp()
                }else -> {
                viewModel.removeOkSnackBar()
                viewModel.showConfigSnackBar()
                }
            }
        }

        networkStateMonitor = NetworkStateMonitor(this, viewModel)
    }


    public override fun onStart() {
        super.onStart()

        //S'hauria de ficar al onCreate() els observeAsState()

        setContent {
            viewModel.selectedHome.observeAsState().value
            viewModel.selectedPlace.observeAsState().value
            viewModel.selectedArrow.observeAsState().value
            viewModel.selectedBuy.observeAsState().value
            viewModel.closingDialogue.observeAsState().value
            viewModel.transactions.observeAsState().value
            viewModel.expanded.observeAsState().value
            viewModel.configSnackBar.observeAsState().value
            viewModel.okSnackBar.observeAsState().value
            viewModel.startApp.observeAsState().value
            viewModel.tokens.observeAsState().value
            viewModel.tokens_to_buy.observeAsState().value
            viewModel.tokens_to_sell.observeAsState().value
            viewModel.tokens_to_send.observeAsState().value
            viewModel.valid_tokens_to_buy.observeAsState().value
            viewModel.valid_tokens_to_sell.observeAsState().value
            viewModel.valid_tokens_to_send.observeAsState().value
            viewModel.buy_dialogue.observeAsState().value
            viewModel.sell_dialogue.observeAsState().value
            viewModel.send_dialogue.observeAsState().value
            viewModel.recipient.observeAsState().value
            viewModel.valid_recipient.observeAsState().value
            viewModel.recipient.observeAsState().value
            viewModel.userLatitude.observeAsState().value
            viewModel.userLongitude.observeAsState().value



            Widgets_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!checkPermissions(this) && !viewModel.startApp.value!!){
                        requestPermissions()
                        if(viewModel.okSnackBar.value == true){
                            OkSnackbar(activity = this){ startLocationPermissionRequest() }
                        }
                        else if(viewModel.configSnackBar.value == true){
                            ConfigSnackbar(activity = this,viewModel)
                        }
                    }
                    else{
                        if(ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED)
                        {
                            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                                SettingsDialogue(activity = this, viewModel = viewModel)
                            }
                            else{
                                GetFineLocation(this, mFusedLocationClient, viewModel)
                            }

                        }
                        else{
                            if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                                SettingsDialogue(activity = this, viewModel = viewModel)
                            }
                            else{
                                GetCoarseLocation(this, mFusedLocationClient, viewModel)
                            }

                        }
                        BaseScreen(viewModel = viewModel, this, sharedPreferencesData)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStateMonitor.unregister()
    }

    private fun startLocationPermissionRequest(){
        locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                                  Manifest.permission.ACCESS_COARSE_LOCATION))
    }


    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            /*showSnackbar(
                R.string.permission_rationale, android.R.string.ok
            ) { // Request permission
                startLocationPermissionRequest()
            }*/
            viewModel.removeConfigSnackBar()
            viewModel.showOkSnackBar()

        } else {
            Log.i(TAG, "Requesting permission")
            viewModel.removeOkSnackBar()
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            viewModel.showConfigSnackBar()
            viewModel.add1SettingsTimer()
            startLocationPermissionRequest()
        }
    }

}

fun backAction(viewModel: SendMoneyViewModel){
    if (viewModel.selectedHome.value == true){
        viewModel.showClosingDialogue()
    }
    else {
        viewModel.unSelectEverything()
        viewModel.selectHome()
    }

}









