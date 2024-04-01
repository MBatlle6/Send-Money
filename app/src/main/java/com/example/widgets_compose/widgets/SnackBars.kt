package com.example.widgets_compose.widgets

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel
import kotlinx.coroutines.launch

@Composable
fun ConfigSnackbar(activity: MainActivity, viewModel: SendMoneyViewModel) {

    if (viewModel.settingsTimer.value!! < 4){ //Nº de veces que el contador se incrementa
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = activity.getString(R.string.advice))},
                    icon = {},
                    onClick = {
                        scope.launch {
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = activity.getString(R.string.permission_denied_explanation),
                                    actionLabel = activity.getString(R.string.settings),
                                    // Defaults to SnackbarDuration.Short
                                    duration = SnackbarDuration.Indefinite
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    val intent = Intent()
                                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    val uri = Uri.fromParts(
                                        "package",
                                        activity.getString(R.string.app_id), null
                                    )
                                    intent.data = uri
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    activity.startActivity(intent)
                                }
                                SnackbarResult.Dismissed -> {

                                }
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            // Screen content
        }
    }

}

@Composable
fun OkSnackbar(activity: MainActivity, startLocationPermissionRequest: () ->Unit) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = activity.getString(R.string.advice))},
                icon = {},
                onClick = {
                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = activity.getString(R.string.permission_rationale),
                                actionLabel = activity.getString(android.R.string.ok),
                                // Defaults to SnackbarDuration.Short
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                startLocationPermissionRequest()
                            }
                            SnackbarResult.Dismissed -> {

                            }
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        // Screen content
    }
}