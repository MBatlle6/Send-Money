package com.example.widgets_compose.screens

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.widgets.BottomBar
import com.example.widgets_compose.widgets.ClosingDialogue
import com.example.widgets_compose.screens.app.BuyScreen
import com.example.widgets_compose.screens.app.HomeScreen
import com.example.widgets_compose.screens.app.LocationScreen
import com.example.widgets_compose.screens.app.SendScreen
import com.example.widgets_compose.widgets.TopBar
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData
import com.example.widgets_compose.screens.app.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    viewModel: SendMoneyViewModel,
    activity: MainActivity,
    //sharedPreferencesData: SharedPreferencesData
) {
    if(viewModel.closingDialogue.value == true) ClosingDialogue(viewModel = viewModel, activity = activity)
    Scaffold(
        topBar = { TopBar(viewModel = viewModel, activity) },
        content = {
            if(viewModel.selectedHome.value == true){
                //viewModel.getTransaction()
                HomeScreen(viewModel, activity)
            }
            else if (viewModel.selectedArrow.value == true) SendScreen(viewModel, activity)
            else if (viewModel.selectedPlace.value == true) LocationScreen(viewModel, activity)
            else if (viewModel.selectedSettings.value == true) SettingsScreen(viewModel, activity)
            else BuyScreen(viewModel, activity)

        },
        bottomBar = { BottomBar(viewModel) },
    )
}













