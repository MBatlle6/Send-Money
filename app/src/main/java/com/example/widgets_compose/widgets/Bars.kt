package com.example.widgets_compose.widgets

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.backAction
import com.example.widgets_compose.ui.theme.Turquoise


@Composable
 fun BottomBar(viewModel: SendMoneyViewModel) {
    BottomAppBar(
        containerColor = Turquoise,
        modifier = Modifier.height(64.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { viewModel.unSelectEverything()
                    viewModel.selectHome()
                },
                modifier = Modifier.padding(end = 10.dp)
            ){
                Icon(
                    Icons.Sharp.Home,
                    contentDescription = "Localized description",
                    tint = if (viewModel.selectedHome.value == true) Color.Black else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(
                onClick = { viewModel.unSelectEverything()
                    viewModel.selectSend()
                },
                modifier = Modifier.padding(end = 10.dp)) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    tint = if (viewModel.selectedArrow.value  == true) Color.Black else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = { viewModel.unSelectEverything()
                    viewModel.selectPlace()
                },
                modifier = Modifier.padding(end = 10.dp)) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "Localized description",
                    tint = if (viewModel.selectedPlace.value  == true) Color.Black else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = { viewModel.unSelectEverything()
                    viewModel.selectBuy()
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Localized description",
                    tint = if (viewModel.selectedBuy.value  == true) Color.Black else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: SendMoneyViewModel, activity: ComponentActivity){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Turquoise),
        modifier = Modifier.height(48.dp),
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    backAction(viewModel)
                 }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        },
    )
}





