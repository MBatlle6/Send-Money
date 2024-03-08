package com.example.widgets_compose.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.ui.theme.Turquoise

@Composable
fun TokensToBuyWrittingButton(label: String, viewModel: SendMoneyViewModel) {

    OutlinedTextField(
        isError = !viewModel.valid_tokens_to_buy.value!!,
        value =
        if(viewModel.tokens_to_buy.value == 0){
            ""
        } else {
            viewModel.tokens_to_buy.value.toString()
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "")
        },
        onValueChange = {
            if (!it.contains("[^0-9]".toRegex()) && it.length < 5  && it.isNotEmpty()) {
                viewModel.setTokensToBuy(it.toInt())
            }
            if (it.isEmpty()){
                viewModel.setTokensToBuy(0)
            }
        },
        singleLine = true,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .padding(0.dp, 50.dp, 0.dp, 20.dp)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { /* Acción al presionar "Done" en el teclado */ })
    )
}

@Composable
fun TokensToSellWrittingButton(label: String, viewModel: SendMoneyViewModel) {

    OutlinedTextField(
        isError = !viewModel.valid_tokens_to_sell.value!!,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
        },
        value =
        if(viewModel.tokens_to_sell.value == 0){
            ""
        } else {
            viewModel.tokens_to_sell.value.toString()
        },
        onValueChange = {
            if (!it.contains("[^0-9]".toRegex()) && it.length < 5  && it.isNotEmpty()) {
                viewModel.setTokensToSell(it.toInt())
            }
            if (it.isEmpty()){
                viewModel.setTokensToSell(0)
            }
        },
        singleLine = true,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .padding(0.dp, 50.dp, 0.dp, 20.dp)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { /* Acción al presionar "Done" en el teclado */ })
    )
}


@Composable
fun BuyTokensButton(label: String, viewModel: SendMoneyViewModel){
    Button(
        colors = ButtonDefaults.buttonColors(Turquoise),
        onClick = {
            if(viewModel.tokens_to_buy.value == 0){
                viewModel.changeValidityTokensToBuy(false)
            }else{
                viewModel.changeValidityTokensToBuy(true)
                viewModel.showBuyDialogue(true)
                //Buy Tokens
            }
        }
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = label
        )
    }
}

@Composable
fun SellTokensButton(label: String, viewModel: SendMoneyViewModel){
    Button(
        colors = ButtonDefaults.buttonColors(Turquoise),
        onClick = {
            if(viewModel.tokens_to_sell.value == 0 || viewModel.tokens_to_sell.value!! > viewModel.tokens.value!! ){
                viewModel.changeValidityTokensToSell(false)
            }else{
                viewModel.changeValidityTokensToSell(true)
                viewModel.showSellDialogue(true)
                //Sell Tokens
            }
        }
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = label
        )
    }
}