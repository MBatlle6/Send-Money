package com.example.widgets_compose.screens.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.widgets.TokensToBuyWrittingButton
import com.example.widgets_compose.R
import com.example.widgets_compose.SharedPreferencesData
import com.example.widgets_compose.widgets.BuyDialogue
import com.example.widgets_compose.widgets.BuyTokensButton
import com.example.widgets_compose.widgets.SellDialogue
import com.example.widgets_compose.widgets.SellTokensButton
import com.example.widgets_compose.widgets.TokensToSellWrittingButton

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BuyScreen(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
) {
    if (viewModel.buy_dialogue.value!!){
        BuyDialogue(viewModel = viewModel, activity = activity)
    }
    if (viewModel.sell_dialogue.value!!){
        SellDialogue(viewModel = viewModel, activity = activity)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        //Spacer(modifier = Modifier.size(90.dp))
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Shopping cart",
                modifier = Modifier.size(32.dp)
            )

        Spacer(modifier = Modifier.size(30.dp))
        TokensToBuyWrittingButton(label = activity.getString(R.string.buy_tokens), viewModel = viewModel)
        BuyTokensButton(label = activity.getString(R.string.buy_tokens), viewModel = viewModel)
        Spacer(modifier = Modifier.size(30.dp))
        TokensToSellWrittingButton(label = activity.getString(R.string.sell_tokens), viewModel = viewModel)
        SellTokensButton(label = activity.getString(R.string.sell_tokens), viewModel = viewModel)
    }
}