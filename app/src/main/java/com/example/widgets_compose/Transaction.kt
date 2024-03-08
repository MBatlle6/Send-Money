package com.example.widgets_compose

import java.time.LocalDate

data class Transaction(
    val SENDER: String,
    val RECIPIENT: String,
    val DATE: LocalDate,
    val AMOUNT: Int,
)

fun addTransaction(viewModel: SendMoneyViewModel){

}

fun calculateTokens(viewModel: SendMoneyViewModel){

}