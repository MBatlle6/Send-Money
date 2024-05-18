package com.example.widgets_compose

import SendMoneyViewModelFactory
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import com.example.widgets_compose.messaging.MyFirebaseMessagingService
import com.example.widgets_compose.screens.BaseScreen
import com.example.widgets_compose.ui.theme.Widgets_ComposeTheme
import com.example.widgets_compose.widgets.ConfigSnackbar
import com.example.widgets_compose.widgets.OkSnackbar
import com.example.widgets_compose.widgets.SettingsDialogue
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {


    private lateinit var networkStateMonitor: NetworkStateMonitor
    private lateinit var networkState: LiveData<Boolean>
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG,
            ).show()
        }
    }


    private val sharedPreferencesData by lazy {
        SharedPreferencesData.getInstance(this)
    }

    private val viewModel: SendMoneyViewModel by viewModels {
        SendMoneyViewModelFactory(sharedPreferencesData, sharedPreferencesData)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true){  //Gestionar cuanda el user hace return
        override fun handleOnBackPressed() {
            backAction(viewModel)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {


        signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            onSignInResult(res, this, viewModel)
        }
        if(!isAuthClient()){
            signInLauncher.launch(getAuthIntent())
            viewModel.setLogged(true)
        }


        super.onCreate(savedInstanceState)
        viewModel.getAllowAllConnections()
        viewModel.getShowTransactionTokens()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        /*
        // Create channel to show notifications.
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = "Default"
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW,
            ),
        )
         */

        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.getString(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }

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
        askNotificationPermission()



    }


    public override fun onStart() {
        super.onStart()
        setContent {
            viewModel.selectedHome.observeAsState().value
            viewModel.selectedPlace.observeAsState().value
            viewModel.selectedArrow.observeAsState().value
            viewModel.selectedBuy.observeAsState().value
            viewModel.closingDialogue.observeAsState().value
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
            viewModel.otherUserLocation.observeAsState().value
            viewModel.otherUserLatitude.observeAsState().value
            viewModel.otherUserLongitude.observeAsState().value
            viewModel.sell_dialogue.observeAsState().value
            viewModel.location.observeAsState().value
            viewModel.send_dialogue.observeAsState().value
            viewModel.recipient.observeAsState().value
            viewModel.valid_recipient.observeAsState().value
            viewModel.recipient.observeAsState().value
            viewModel.userLatitude.observeAsState().value
            viewModel.userLongitude.observeAsState().value
            viewModel.allowAllConnections.observeAsState().value
            viewModel.settingsDialogue.observeAsState().value
            viewModel.isLogged.observeAsState().value
            viewModel.deleteAccountDialogue.observeAsState().value
            viewModel.signOutDialogue.observeAsState().value
            viewModel.otherUserEmail.observeAsState().value
            viewModel.otherUserLocation.observeAsState().value
            viewModel.previousLocation.observeAsState().value
            viewModel.currentLocation.observeAsState().value
            viewModel.currentTokens.observeAsState().value
            viewModel.resetPasswordEmailDialogue.observeAsState().value
            viewModel.transactionTokens.observeAsState().value
            viewModel.transactionDates.observeAsState().value
            viewModel.transactions.observeAsState().value

            if (auth.currentUser != null){
                viewModel.getTokens()
            }


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
                            GetFineLocation(this, mFusedLocationClient, viewModel)

                        }
                        else GetCoarseLocation(this, mFusedLocationClient, viewModel)
                        if(!isAuthClient() && !viewModel.isLogged.value!!){
                            signInLauncher.launch(getAuthIntent())
                        }
                        Firebase.messaging.token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                                    return@OnCompleteListener
                                }

                                // Get new FCM registration token
                                val token = task.result
                                val myClass = MyFirebaseMessagingService()
                                if(isAuthClient()) myClass.sendRegistrationToServer(token)
                            },
                        )
                        BaseScreen(viewModel = viewModel, this/*, sharedPreferencesData*/)
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

    private fun askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }

}


fun backAction(viewModel: SendMoneyViewModel){
    if (viewModel.selectedHome.value!!){
        viewModel.showClosingDialogue()
    }
    else if (viewModel.selectedPlace.value!!){
        if (viewModel.currentLocation.value!! || viewModel.previousLocation.value!! || viewModel.otherUserLocation.value!!){
            viewModel.showCurrentLocation(false)
            viewModel.showPreviousLocation(false)
            viewModel.showOtherUserLocation(false)
        }
        else{
            viewModel.unSelectEverything()
            viewModel.selectHome()
        }
    }
    else {
        viewModel.unSelectEverything()
        viewModel.selectHome()
    }

}











