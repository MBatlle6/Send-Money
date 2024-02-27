package com.example.widgets_compose

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

data class Transaction(
    val SENDER: String,
    val RECIPIENT: String,
    val DATE: LocalDate,
    val AMOUNT: Double,
)