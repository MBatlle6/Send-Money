package com.example.widgets_compose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class SendMoneyViewModel(private val sharedPreferencesData: SharedPreferencesData): ViewModel() {


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
        mutableListOf()
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

    fun getTokens() : Int{
        return sharedPreferencesData.getTokens()
    }

    fun setTokens(amount : Int){
        sharedPreferencesData.saveTokens(amount)
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