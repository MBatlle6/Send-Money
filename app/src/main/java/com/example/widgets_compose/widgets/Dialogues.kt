package com.example.widgets_compose.widgets

import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.Transaction
import com.example.widgets_compose.backAction
import java.time.LocalDate

@Composable
fun ClosingDialogue(viewModel: SendMoneyViewModel, activity: MainActivity){
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
fun BuyDialogue(viewModel: SendMoneyViewModel, activity: MainActivity){
    AlertDialog(
        onDismissRequest = { viewModel.showBuyDialogue(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.showBuyDialogue(false)
                    viewModel.addTransaction(
                        Transaction(activity.getString(R.string.bank), activity.getString(R.string.me),
                                    LocalDate.now(), viewModel.tokens_to_buy.value!!)
                    )
                    //calculateTokens(viewModel, activity)
                    viewModel.setTokens(viewModel.tokens.value!! + viewModel.tokens_to_buy.value!!)
                    viewModel.setTokensToBuy(0)
                    viewModel.setTokensToSell(0)
                    backAction(viewModel)
                })
            {
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
fun SellDialogue(viewModel: SendMoneyViewModel, activity: MainActivity){
    AlertDialog(
        onDismissRequest = { viewModel.showSellDialogue(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.showSellDialogue(false)
                    viewModel.addTransaction(
                        Transaction(activity.getString(R.string.me), activity.getString(R.string.bank),
                            LocalDate.now(), viewModel.tokens_to_sell.value!!)
                    )
                    viewModel.setTokens(viewModel.tokens.value!! - viewModel.tokens_to_sell.value!!)
                    viewModel.setTokensToSell(0)
                    viewModel.setTokensToBuy(0)
                    backAction(viewModel)
                }
            )
            {
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

@Composable
fun SendDialogue(viewModel: SendMoneyViewModel, activity: MainActivity){
    AlertDialog(
        onDismissRequest = { viewModel.showSendDialogue(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.showSendDialogue(false)
                    viewModel.addTransaction(
                        Transaction(activity.getString(R.string.me), viewModel.recipient.value!!,
                            LocalDate.now(), viewModel.tokens_to_send.value!!)
                    )
                    viewModel.setTokens(viewModel.tokens.value!! - viewModel.tokens_to_send.value!!)
                    viewModel.setTokensToSend(0)
                    viewModel.setRecipient("")
                    backAction(viewModel)
                }
            )
            {
                Text(text = activity.getString(R.string.send))
            }
        },
        dismissButton = {
            TextButton(onClick = {viewModel.showSendDialogue(false)}) {
                Text(text = activity.getString(R.string.go_back))
            }
        },
        title = { Text(text = activity.getString(R.string.send_tokens) )},
        text = { Text(text = activity.getString(R.string.transaction_request_1st_part)+ " " + activity.getString(R.string.to) + " "
                + viewModel.recipient.value + " "
                + viewModel.tokens_to_send.value.toString() +" "+ activity.getString(R.string.tokens)) },

        )
}

@Composable
fun SettingsDialogue(activity: MainActivity, viewModel: SendMoneyViewModel){
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    viewModel.showSettingsDialogue(false)
                }) {
                    Text(text = activity.getString(R.string.activateLocationSettings))
                }
            },
            title = { Text(text = activity.getString(R.string.locationSettingsUnable)) },
            text = { Text(text = activity.getString(R.string.pleaseActivateLoacationSettings)) },

            )
}