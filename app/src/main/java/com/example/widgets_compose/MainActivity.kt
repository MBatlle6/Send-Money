package com.example.widgets_compose

import android.Manifest
import android.content.ContentValues.TAG
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
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.widgets_compose.screens.BaseScreen
import com.example.widgets_compose.ui.theme.Widgets_ComposeTheme
import com.example.widgets_compose.widgets.ConfigSnackbar
import com.example.widgets_compose.widgets.OkSnackbar

//Commit de prova

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WidgetsViewModel>()
    private var settings = false
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>

    private val onBackPressedCallback = object : OnBackPressedCallback(true){  //Gestionar cuanda el user hace return
        override fun handleOnBackPressed() {
            backAction(viewModel)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }


    public override fun onStart() {
        super.onStart()
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
                        Text(text = "No hi ha permissos")

                    }
                    else{
                        BaseScreen(viewModel = viewModel, this)
                    }
                }
            }
        }
    }


    fun startLocationPermissionRequest(){
        locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                                  Manifest.permission.ACCESS_COARSE_LOCATION))
    }


    fun requestPermissions() {
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

fun backAction(viewModel: WidgetsViewModel){
    if (viewModel.selectedHome.value == true){
        viewModel.showClosingDialogue()
    }
    else {
        viewModel.unSelectEverything()
        viewModel.selectHome()
    }

}









