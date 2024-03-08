package com.example.widgets_compose.widgets

import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel

@Composable
fun ClosingDialogue(viewModel: SendMoneyViewModel, activity: ComponentActivity){
    if(viewModel.closingDialogue.value == true){
        AlertDialog(
            onDismissRequest = { viewModel.hideClosingDialogue() },
            confirmButton = {
                TextButton(onClick = {activity.finish()}) {
                    Text(text = activity.getString(R.string.closing_app_title))
                }
            },
            dismissButton = {
                TextButton(onClick = {viewModel.hideClosingDialogue()}) {
                    Text(text = activity.getString(R.string.remain))
                }
            },
            title = { Text(text = activity.getString(R.string.closing_app_title)) },
            text = { Text(text = activity.getString(R.string.closing_app)) },

            )
    }
}

@Composable
fun BuyDialogue(viewModel: SendMoneyViewModel, activity: ComponentActivity){
    AlertDialog(
        onDismissRequest = { viewModel.showBuyDialogue(false) },
        confirmButton = {
            TextButton(onClick = {activity.finish()}) {
                Text(text = activity.getString(R.string.buy))
            }
            },
        dismissButton = {
            TextButton(onClick = {viewModel.showBuyDialogue(false)}) {
                Text(text = activity.getString(R.string.go_back))
            }
                        },
        title = { Text(text = activity.getString(R.string.buy_tokens) )},
        text = { Text(text = activity.getString(R.string.buy_tokens_request) + " "
                + viewModel.tokens_to_buy.value.toString() +" "+ activity.getString(R.string.tokens)) },

        )
}

@Composable
fun SellDialogue(viewModel: SendMoneyViewModel, activity: ComponentActivity){
    AlertDialog(
        onDismissRequest = { viewModel.showSellDialogue(false) },
        confirmButton = {
            TextButton(onClick = {activity.finish()}) {
                Text(text = activity.getString(R.string.sell))
            }
        },
        dismissButton = {
            TextButton(onClick = {viewModel.showSellDialogue(false)}) {
                Text(text = activity.getString(R.string.go_back))
            }
        },
        title = { Text(text = activity.getString(R.string.sell_tokens) )},
        text = { Text(text = activity.getString(R.string.sell_tokens_request) + " "
                + viewModel.tokens_to_sell.value.toString() +" "+ activity.getString(R.string.tokens)) },

        )
}