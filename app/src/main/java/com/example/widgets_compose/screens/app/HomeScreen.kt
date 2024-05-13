package com.example.widgets_compose.screens.app

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.widgets_compose.R
import com.example.widgets_compose.Transaction
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.ui.theme.Turquoise

@Composable
 fun HomeScreen(
    viewModel: SendMoneyViewModel,
    activity: ComponentActivity
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "SendMoney Logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(210.dp)
        )
        if(viewModel.transactionTokens.value!!){
            Text(
                text = viewModel.getTokens().toString()  + " " + activity.getString(R.string.tokens_abreviation),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
        else{
            Text(
                text = "**",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        if (viewModel.transactions.value?.isEmpty() == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = activity.getString(R.string.there_not_transactions),
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            if (viewModel.transactions.value!!.size <= 3){
                Column {
                    var i = 0
                    while(i < viewModel.transactions.value?.size!!){
                        TransactionItem(transaction = viewModel.transactions.value!![i], activity, viewModel)
                        i++
                    }
                }
            }
            else{
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 300.dp) //Provar a ver
                ) {
                    val itemsToShow = if (viewModel.expanded.value!!)
                        viewModel.transactions.value?.size else 3
                    if (itemsToShow != null) {

                        items(itemsToShow) { index ->
                            val transaction = viewModel.transactions.value!![index]
                            transaction.let { TransactionItem(
                                transaction = it,
                                activity = activity,
                                viewModel = viewModel
                            ) }
                        }
                    }
                }

                if (!viewModel.expanded.value!!) {
                    Text(
                        text = activity.getString(R.string.see_more),
                        modifier = Modifier.clickable { viewModel.expand() }
                    )
                }
                if(viewModel.expanded.value == true){
                    Text(
                        text = activity.getString(R.string.see_less),
                        modifier = Modifier.clickable { viewModel.shrink()}
                    )
                }
            }
        }

    }
}


@Composable
fun TransactionItem(
    transaction: Transaction,
    activity: ComponentActivity,
    viewModel: SendMoneyViewModel
) {
    //val isSent = transaction.SENDER == Firebase.auth.currentUser?.email   #Para el Firebase (No borrar)
    val isSent: Boolean
    if (transaction.SENDER == activity.getString(R.string.me)) isSent = true else isSent= false
    val sign = if (isSent) "-" else "+"
    val signColor = if (isSent) Color.Red else Turquoise
    val arrowIcon = if (isSent) Icons.AutoMirrored.Filled.ArrowBack else Icons.AutoMirrored.Filled.ArrowForward
    val arrowColor = if (isSent) Color.Red else Turquoise

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = arrowIcon,
            contentDescription = null,
            tint = arrowColor,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (isSent) transaction.RECIPIENT else transaction.SENDER,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if(viewModel.transactionDates.value!!){
                Text(
                    text = transaction.DATE.toString(),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Text(
            text = "$sign${transaction.AMOUNT}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = signColor
        )
    }
}


