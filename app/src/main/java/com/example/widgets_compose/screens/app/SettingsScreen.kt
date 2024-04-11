package com.example.widgets_compose.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData
import com.example.widgets_compose.ui.theme.Turquoise

@Composable
fun SettingsScreen(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = activity.getString(R.string.network),
            fontSize = 30.sp,
            )
        Spacer(modifier = Modifier.height(16.dp))
        CheckList(viewModel = viewModel, activity = activity)
    }
}


@Composable
fun CheckList(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
){
    Row(
        verticalAlignment = Alignment.CenterVertically // Add this line
    ) {
        Checkbox(
            checked = viewModel.allowAllConnections.value!!,
            onCheckedChange = {
                if (!viewModel.allowAllConnections.value!!){
                    viewModel.setAllowAllConnections(true)
                }
            },
        )
        Text(
            text = activity.getString(R.string.allowAllConnections),
            fontSize = 20.sp,
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically // Add this line
    ) {
        Checkbox(
            checked = !viewModel.allowAllConnections.value!!,
            onCheckedChange = {
                              if (viewModel.allowAllConnections.value!!){
                                  viewModel.setAllowAllConnections(false)
                              }
            },
        )
        Text(
            text = activity.getString(R.string.justWifi),
            fontSize = 20.sp,
        )
    }
}

