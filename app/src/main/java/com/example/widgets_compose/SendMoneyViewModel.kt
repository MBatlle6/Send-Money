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
    val tokens = MutableLiveData<Int>(0)
    val tokens_to_buy = MutableLiveData<Int>(0)
    val tokens_to_sell = MutableLiveData<Int>(0)
    val valid_tokens_to_buy = MutableLiveData<Boolean>(true)
    val valid_tokens_to_sell = MutableLiveData<Boolean>(true)
    val buy_dialogue = MutableLiveData<Boolean>(false)
    val sell_dialogue = MutableLiveData<Boolean>(false)
    val send_dialogue = MutableLiveData<Boolean>(false)
    val valid_tokens_to_send = MutableLiveData<Boolean>(true)
    val tokens_to_send = MutableLiveData<Int>(0)
    val recipient = MutableLiveData<String>("")
    val valid_recipient = MutableLiveData<Boolean>(true)
    val userLatitude = MutableLiveData<Double>(0.0)
    val userLongitude = MutableLiveData<Double>(0.0)
    val settingsDialogue = MutableLiveData(false)

    val transactions = MutableLiveData<MutableList<Transaction>>(
        mutableListOf(
        Transaction("Me", "Juan", LocalDate.now(), 100),
        Transaction("Pedro", "Me", LocalDate.now(), 50),
        Transaction("Me", "María", LocalDate.now(), 75),
        Transaction("Luis", "Me", LocalDate.now(), 120),
        Transaction("Él", "Me", LocalDate.now(), 200),

    )
    )

    fun showSettingsDialogue(show : Boolean){
        send_dialogue.value = show
    }

    fun setUserLongitude(longitude : Double){
        userLongitude.value = longitude
    }

    fun setUserLatitude(latitude : Double){
        userLatitude.value = latitude
    }

    fun setRecipient(recipienT: String){
        recipient.value = recipienT
    }
    fun changeRecipientValidity(valid: Boolean){
        valid_recipient.value = valid
    }

    fun setTokens(amount : Int){
        tokens.value = amount
    }

    fun addTransaction(transaction: Transaction){
        transactions.value!!.add(0,transaction)
    }

    fun showSendDialogue(show: Boolean){
        send_dialogue.value = show
    }

    fun changeValidityTokensToSend(valid : Boolean){
        valid_tokens_to_send.value = valid
    }

    fun setTokensToSend(tokens_amount : Int){
        tokens_to_send.value = tokens_amount
    }

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