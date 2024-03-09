package com.example.widgets_compose.screens.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.widgets.RecipientWritingButton
import com.example.widgets_compose.widgets.SellDialogue
import com.example.widgets_compose.widgets.SendDialogue
import com.example.widgets_compose.widgets.SendTokensButton
import com.example.widgets_compose.widgets.TokensToSendWrittingButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SendScreen(viewModel: SendMoneyViewModel, activity: MainActivity) {
    if (viewModel.send_dialogue.value!!){
        SendDialogue(viewModel = viewModel, activity = activity)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.AutoMirrored.Filled.Send,
            contentDescription = "Localized description",
            modifier = Modifier.size(32.dp)
        )
        //Spacer(modifier = Modifier.size(30.dp))
        RecipientWritingButton(label = activity.getString(R.string.recipient_email), viewModel = viewModel)
        TokensToSendWrittingButton(label = activity.getString(R.string.send_tokens), viewModel = viewModel)
        SendTokensButton(label = activity.getString(R.string.send_tokens), viewModel = viewModel)


    }
}