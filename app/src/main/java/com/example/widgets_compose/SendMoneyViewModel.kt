package com.example.widgets_compose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class SendMoneyViewModel: ViewModel() {


    val selectedHome = MutableLiveData<Boolean>(true)
    val selectedArrow = MutableLiveData<Boolean>(false)
    val selectedBuy = MutableLiveData<Boolean>(false)
    val selectedPlace = MutableLiveData<Boolean>(false)
    val closingDialogue = MutableLiveData<Boolean>(false)
    val expanded = MutableLiveData<Boolean>(false)
    val configSnackBar = MutableLiveData<Boolean>(false)
    val okSnackBar = MutableLiveData<Boolean>(false)
    val startApp = MutableLiveData<Boolean>(false)
    val settingsTimer = MutableLiveData<Int>(0)
    val tokens = MutableLiveData<Int>(100)
    val tokens_to_buy = MutableLiveData<Int>(0)
    val tokens_to_sell = MutableLiveData<Int>(0)
    val valid_tokens_to_buy = MutableLiveData<Boolean>(true)
    val valid_tokens_to_sell = MutableLiveData<Boolean>(true)
    val buy_dialogue = MutableLiveData<Boolean>(false)
    val sell_dialogue = MutableLiveData<Boolean>(false)

    val transactions = MutableLiveData<List<Transaction>>(listOf(
        Transaction("Yo", "Juan", LocalDate.now(), 100),
        Transaction("Pedro", "Yo", LocalDate.now(), 50),
        Transaction("Yo", "María", LocalDate.now(), 75),
        Transaction("Yo", "Luis", LocalDate.now(), 120),
        Transaction("Él", "Yo", LocalDate.now(), 200),
        Transaction("Yo", "Juan", LocalDate.now(), 100),
        Transaction("Pedro", "Yo", LocalDate.now(), 50),
        Transaction("Yo", "María", LocalDate.now(), 75),
        Transaction("Yo", "Luis", LocalDate.now(), 120),
        Transaction("Él", "Yo", LocalDate.now(), 200),
        Transaction("Yo", "Juan", LocalDate.now(), 100),
        Transaction("Pedro", "Yo", LocalDate.now(), 50),
        Transaction("Yo", "María", LocalDate.now(), 75),
        Transaction("Yo", "Luis", LocalDate.now(), 120),
    ))


    fun showBuyDialogue(show: Boolean){
        buy_dialogue.value = show
    }
    fun showSellDialogue(show: Boolean){
        sell_dialogue.value = show
    }
    fun changeValidityTokensToSell(valid : Boolean){
        valid_tokens_to_sell.value = valid
    }

    fun changeValidityTokensToBuy(valid : Boolean){
        valid_tokens_to_buy.value = valid
    }


    fun setTokensToBuy(tokens_amount : Int){
        tokens_to_buy.value = tokens_amount
    }

    fun setTokensToSell(tokens_amount : Int){
        tokens_to_sell.value = tokens_amount
    }


    fun add1SettingsTimer(){
        settingsTimer.value = settingsTimer.value!! + 1
    }

    fun unSelectEverything() {
        selectedHome.value = false
        selectedArrow.value = false
        selectedBuy.value = false
        selectedPlace.value = false
        }

    fun selectHome(){
        selectedHome.value = true
    }

    fun selectSend(){
        selectedArrow.value = true
    }

    fun selectBuy(){
        selectedBuy.value = true
    }

    fun selectPlace(){
        selectedPlace.value = true
    }

    fun showClosingDialogue(){
        closingDialogue.value = true
    }

    fun hideClosingDialogue(){
        closingDialogue.value = false
    }

    fun expand(){
        expanded.value = true
    }

    fun shrink(){
        expanded.value = false
    }

    fun showConfigSnackBar(){
        configSnackBar.value = true
    }

    fun removeConfigSnackBar(){
        configSnackBar.value = false
    }

    fun showOkSnackBar(){
        okSnackBar.value = true
    }

    fun removeOkSnackBar(){
        okSnackBar.value = false
    }

    fun startApp(){
        startApp.value = true
    }

}