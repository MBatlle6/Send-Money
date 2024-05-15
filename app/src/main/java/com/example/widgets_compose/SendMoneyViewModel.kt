package com.example.widgets_compose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.widgets_compose.messaging.MyFirebaseMessagingService
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNREACHABLE_CODE")
class SendMoneyViewModel(private val sharedPreferencesData: SharedPreferencesData, private val sharedPreferencesConnection:SharedPreferencesData ): ViewModel() {


    val selectedHome = MutableLiveData<Boolean>(true)
    val selectedArrow = MutableLiveData<Boolean>(false)
    val selectedBuy = MutableLiveData<Boolean>(false)
    val selectedPlace = MutableLiveData<Boolean>(false)
    val selectedSettings = MutableLiveData<Boolean>(false)
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
    val allowAllConnections = MutableLiveData(true)
    val isLogged = MutableLiveData(false)
    val signOutDialogue = MutableLiveData(false)
    val deleteAccountDialogue = MutableLiveData(false)
    val otherUserEmail = MutableLiveData<String>("")
    val currentLocation = MutableLiveData(false)
    val previousLocation = MutableLiveData(false)
    val otherUserLocation = MutableLiveData(false)
    val transactionTokens = MutableLiveData(true)
    val transactionDates = MutableLiveData(true)
    val resetPasswordEmailDialogue = MutableLiveData(false)
    val currentUser = auth.currentUser
<<<<<<< HEAD
=======

>>>>>>> 4a06226852111d64c6255148120aa5886abe9df0
    private val _currentTokens = MutableLiveData<Int>()
    val currentTokens: LiveData<Int> = _currentTokens

    val transactions = MutableLiveData<MutableList<Transaction>>(
        mutableListOf()
    )

    fun showTransactionDates(show: Boolean) {
        transactionDates.value = show
    }

    fun showTransactionTokens(show: Boolean) {
        transactionTokens.value = show
    }

    fun showResetPasswordEmailDialogue(show: Boolean) {
        resetPasswordEmailDialogue.value = show
    }

    fun showCurrentLocation(show: Boolean) {
        currentLocation.value = show
    }

    fun showPreviousLocation(show: Boolean) {
        previousLocation.value = show
    }

    fun showOtherUserLocation(show: Boolean) {
        otherUserLocation.value = show
    }

    fun setOtherUserEmail(value: String) {
        otherUserEmail.value = value
    }

    fun showSignOutDialogue(show: Boolean) {
        signOutDialogue.value = show
    }

    fun showDeleteAccountDialogue(show: Boolean) {
        deleteAccountDialogue.value = show
    }

    fun setLogged(value: Boolean) {
        isLogged.value = value
    }


    fun setAllowAllConnections(allow: Boolean) {
        allowAllConnections.value = allow
        sharedPreferencesConnection.setConnections(allowAllConnections.value!!)
    }

    fun getAllowAllConnections() {
        allowAllConnections.value = sharedPreferencesConnection.getConnections()
    }


    fun getTransaction() {
        transactions.value = sharedPreferencesData.getTransactions(transactions.value!!)
        //El problema està aquí!!
    }

    fun showSettingsDialogue(show: Boolean) {
        settingsDialogue.value = show
    }

    fun setUserLongitude(longitude: Double) {
        userLongitude.value = longitude
    }

    fun setUserLatitude(latitude: Double) {
        userLatitude.value = latitude
    }

    fun setRecipient(recipienT: String) {
        recipient.value = recipienT
    }

    fun changeRecipientValidity(valid: Boolean) {
        valid_recipient.value = valid
    }

    fun getTokens(): Task<Int> {
        val db = FirebaseFirestore.getInstance()
        val userDocRef = db.collection("users").document(currentUser!!.uid)
<<<<<<< HEAD
=======

>>>>>>> 4a06226852111d64c6255148120aa5886abe9df0
        val tokensTaskCompletionSource = TaskCompletionSource<Int>()

        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val tokens = documentSnapshot.getLong("tokens")?.toInt() ?: 0
                _currentTokens.value = tokens
                tokensTaskCompletionSource.setResult(tokens)

            } else {
                tokensTaskCompletionSource.setResult(0)
            }
        }.addOnFailureListener { exception ->
            tokensTaskCompletionSource.setException(exception)
        }
        return tokensTaskCompletionSource.task
    }

    // Función para actualizar el número de tokens de un usuario
    fun setTokens(newTokenAmount: Int): Task<Int> {
        val db = FirebaseFirestore.getInstance()
        val userDocRef = db.collection("users").document(currentUser!!.uid)
        return getTokens().addOnSuccessListener { existingTokens ->
            val updatedTokens = newTokenAmount + existingTokens
            userDocRef.update("tokens", updatedTokens)
            val updates = mapOf(
                "location" to mapOf(
                    "latitude" to userLatitude,
                    "longitude" to userLongitude
                )
            )
            db.collection("users").document(currentUser.uid).update(updates)

        }
        getTokens()

    }

    fun getTokensMain(): Int? {
        return currentTokens.value
    }

    fun sellTokens(tokensToSend: Int) {
        // Verifica si hay suficientes tokens para vender
        if (_currentTokens.value!! >= tokensToSend) {
            // Resta los tokens vendidos de currentTokens
            _currentTokens.value = _currentTokens.value!! - tokensToSend
            // Actualiza los tokens en Firestore
            setTokens(_currentTokens.value!!)
        }
    }




    fun addTransaction(transaction: Transaction) {
        transactions.value!!.add(0, transaction)
    }

    fun showSendDialogue(show: Boolean) {
        send_dialogue.value = show
    }

    fun changeValidityTokensToSend(valid: Boolean) {
        valid_tokens_to_send.value = valid
    }

    fun setTokensToSend(tokens_amount: Int) {
        tokens_to_send.value = tokens_amount
    }

    fun showBuyDialogue(show: Boolean) {
        buy_dialogue.value = show
    }

    fun showSellDialogue(show: Boolean) {
        sell_dialogue.value = show
    }

    fun changeValidityTokensToSell(valid: Boolean) {
        valid_tokens_to_sell.value = valid
    }

    fun changeValidityTokensToBuy(valid: Boolean) {
        valid_tokens_to_buy.value = valid
    }


    fun setTokensToBuy(tokens_amount: Int) {
        tokens_to_buy.value = tokens_amount
    }

    fun setTokensToSell(tokens_amount: Int) {
        tokens_to_sell.value = tokens_amount
    }


    fun add1SettingsTimer() {
        settingsTimer.value = settingsTimer.value!! + 1
    }

    fun unSelectEverything() {
        selectedHome.value = false
        selectedArrow.value = false
        selectedBuy.value = false
        selectedPlace.value = false
        selectedSettings.value = false
    }

    fun selectSettings() {
        selectedSettings.value = true
    }

    fun selectHome() {
        selectedHome.value = true
    }

    fun selectSend() {
        selectedArrow.value = true
    }

    fun selectBuy() {
        selectedBuy.value = true
    }

    fun selectPlace() {
        selectedPlace.value = true
    }

    fun showClosingDialogue() {
        closingDialogue.value = true
    }

    fun hideClosingDialogue() {
        closingDialogue.value = false
    }

    fun expand() {
        expanded.value = true
    }

    fun shrink() {
        expanded.value = false
    }

    fun showConfigSnackBar() {
        configSnackBar.value = true
    }

    fun removeConfigSnackBar() {
        configSnackBar.value = false
    }

    fun showOkSnackBar() {
        okSnackBar.value = true
    }

    fun removeOkSnackBar() {
        okSnackBar.value = false
    }

    fun startApp() {
        startApp.value = true
    }

}




