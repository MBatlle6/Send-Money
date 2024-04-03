package com.example.widgets_compose

import java.time.LocalDate

data class Transaction(
    val SENDER: String,
    val RECIPIENT: String,
    val DATE: LocalDate,
    val AMOUNT: Int,
)


//fun calculateTokens(  NOT USED FOR THE MOMENT
//    viewModel: SendMoneyViewModel,
//    activity: MainActivity
//){
//    var i = 0
//    var new_amount :Int = 0
//    while (i < viewModel.transactions.value!!.size){
//        if (viewModel.transactions.value!![i].SENDER == activity.getString(R.string.me)){
//            new_amount = viewModel.tokens.value!! - viewModel.transactions.value!![i].AMOUNT
//            viewModel.setTokens(new_amount)
//        }
//        else{
//            new_amount = viewModel.tokens.value!! + viewModel.transactions.value!![i].AMOUNT
//            viewModel.setTokens(new_amount)
//        }
//            i ++
//    }
//}