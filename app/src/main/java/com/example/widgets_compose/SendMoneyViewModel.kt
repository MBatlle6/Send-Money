package com.example.widgets_compose

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.time.LocalDate

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class SendMoneyViewModel(private val sharedPreferencesShowTokens:SharedPreferencesData,private val sharedPreferencesConnection: SharedPreferencesData ): ViewModel() {


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
    var otherUserLatitude = MutableLiveData<Double>(0.0)
    var otherUserLongitude = MutableLiveData<Double>(0.0)
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
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var mediaPlayer: MediaPlayer
    val isPlaying = MutableLiveData<Boolean>(false)
    private val _isEmailValid = MutableLiveData<Boolean>(false)
    private val _isEmailValidLocation = MutableLiveData<Boolean>(false)
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid
    val isEmailValidLocation: LiveData<Boolean> get() = _isEmailValidLocation


    private val db = FirebaseFirestore.getInstance()
    var tokensToSendInput: Int = 0
    val location = MutableLiveData<GeoPoint>()

    private val _currentTokens = MutableLiveData<Int>()
    val currentTokens: LiveData<Int> = _currentTokens

    val transactions = MutableLiveData<MutableList<Transaction>>(mutableListOf())


    fun addTransaction(transaction: Transaction) {
        val db = FirebaseFirestore.getInstance()
        val userEmail = getCurrentUser()!!.email.toString()

        db.collection("users").document(userEmail).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentTransactions = documentSnapshot.get("transactions") as? List<Map<String, Any>> ?: emptyList()

                    val updatedTransactions = currentTransactions.toMutableList()

                    updatedTransactions.add(0, mapOf(
                        "SENDER" to transaction.SENDER,
                        "RECIPIENT" to transaction.RECIPIENT,
                        "DATE" to transaction.DATE.toString(),
                        "AMOUNT" to transaction.AMOUNT
                    ))

                    db.collection("users").document(userEmail).update("transactions", updatedTransactions)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Transacción añadida con éxito")
                            // También actualizar las transacciones localmente
                            transactions.postValue(updatedTransactions.map {
                                Transaction(
                                    SENDER = it["SENDER"] as String,
                                    RECIPIENT = it["RECIPIENT"] as String,
                                    DATE = LocalDate.parse(it["DATE"] as String),
                                    AMOUNT = (it["AMOUNT"] as Number).toInt()
                                )
                            }.toMutableList())
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error al añadir la transacción", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al leer las transacciones existentes", e)
            }
    }

    fun addTransactionnToSender(transaction: Transaction){
        val db = FirebaseFirestore.getInstance()
        val sender = getCurrentUser()!!.email.toString()
        val otherUser = recipient.value.toString()

        db.collection("users").document(otherUser).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentTransactions = documentSnapshot.get("transactions") as? List<Map<String, Any>> ?: emptyList()

                    val updatedTransactions = currentTransactions.toMutableList()

                    updatedTransactions.add(0, mapOf(
                        "SENDER" to transaction.SENDER,
                        "RECIPIENT" to transaction.RECIPIENT,
                        "DATE" to transaction.DATE.toString(),
                        "AMOUNT" to transaction.AMOUNT
                    ))

                    db.collection("users").document(otherUser).update("transactions", updatedTransactions)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Transacción añadida con éxito")
                            // También actualizar las transacciones localmente
                            transactions.postValue(updatedTransactions.map {
                                Transaction(
                                    SENDER = sender as String,
                                    RECIPIENT = sender as String,
                                    DATE = LocalDate.parse(it["DATE"] as String),
                                    AMOUNT = (it["AMOUNT"] as Number).toInt()
                                )
                            }.toMutableList())
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error al añadir la transacción", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al leer las transacciones existentes", e)
            }
    }

    fun getTransactionsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val userEmail = getCurrentUser()!!.email

        db.collection("users").document(userEmail.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val transactionsList = documentSnapshot.get("transactions") as? List<Map<String, Any>> ?: emptyList()

                    val transactionsData = transactionsList.map {
                        Transaction(
                            SENDER = it["SENDER"] as String,
                            RECIPIENT = it["RECIPIENT"] as String,
                            DATE = LocalDate.parse(it["DATE"] as String),
                            AMOUNT = (it["AMOUNT"] as Number).toInt()
                        )
                    }

                    transactions.postValue(transactionsData.toMutableList())
                } else {
                    Log.d("Firestore", "No se encontró el documento del usuario con el email: $userEmail")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al obtener las transacciones del usuario", e)
            }
    }

    fun playSound() {
        val audioRef = storage.reference.child("coin_sound.mp3")

        val localFile = File.createTempFile("tempSound", "mp3")
        audioRef.getFile(localFile).addOnSuccessListener {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(localFile.absolutePath)
                prepare()
                start()
                setOnCompletionListener {
                    release()
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error al descargar el archivo", exception)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (this::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }


    fun showTransactionTokens(show: Boolean) {
        transactionTokens.value = show
        sharedPreferencesShowTokens.showTokens(transactionTokens.value!!)


    }

    fun getShowTransactionTokens(){
        transactionTokens.value = sharedPreferencesShowTokens.getShowTokens()
    }

    fun setAllowAllConnections(allow: Boolean) {
        allowAllConnections.value = allow
        sharedPreferencesConnection.setConnections(allowAllConnections.value!!)
    }


    fun getAllowAllConnections() {
        allowAllConnections.value = sharedPreferencesConnection.getConnections()
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
        checkEmailExists(value)
    }

    private fun checkEmailExists(email: String) {
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (email != getCurrentUser()!!.email) {
                        _isEmailValid.value = !documents.isEmpty
                    }
                }
                .addOnFailureListener {
                    _isEmailValid.value = false
                }
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
        checkEmailExistsLocation(recipienT)
    }

    private fun checkEmailExistsLocation(email: String) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (email != getCurrentUser()!!.email) {
                    _isEmailValidLocation.value = !documents.isEmpty
                }
            }
            .addOnFailureListener {
                _isEmailValidLocation.value = false
            }
    }

    fun changeRecipientValidity(valid: Boolean) {
        valid_recipient.value = valid
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }



    fun getDatabase () : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    fun getLatitudeAndLongitudeByEmail() {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(otherUserEmail.value.toString())

        userRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, retrieve the location field
                    val geoPoint = documentSnapshot.getGeoPoint("location")
                    if (geoPoint != null) {
                        otherUserLatitude.value = geoPoint.latitude
                        otherUserLongitude.value = geoPoint.longitude
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                println("Error retrieving user location: $exception")
            }
    }


    fun getTokens(): Task<Int> {
        val currentUser = getCurrentUser()
        val db = getDatabase()
        val userDocRef = db.collection("users").document(currentUser!!.email.toString())

        val tokensTaskCompletionSource = TaskCompletionSource<Int>()

        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val tokens = documentSnapshot.getLong("tokens")?.toInt() ?: 0
                _currentTokens.value = tokens

                getTransactionsFromFirestore()

                tokensTaskCompletionSource.setResult(tokens)
            } else {
                tokensTaskCompletionSource.setResult(0)
            }
        }.addOnFailureListener { exception ->
            tokensTaskCompletionSource.setException(exception)
        }

        return tokensTaskCompletionSource.task
    }


    fun setTokens(newTokenAmount: Int): Task<Int> {
        val currentUser = getCurrentUser()
        val db = FirebaseFirestore.getInstance()
        val userDocRef = db.collection("users").document(currentUser!!.email.toString())
        return getTokens().addOnSuccessListener { existingTokens ->
            val updatedTokens = newTokenAmount + existingTokens
            userDocRef.update("tokens", updatedTokens)
            val updates = mapOf(
                "location" to mapOf(
                    "latitude" to userLatitude,
                    "longitude" to userLongitude
                )
            )
            val userLocation = GeoPoint(userLatitude.value!!, userLongitude.value!!)
            val updates2 = hashMapOf<String, Any>(
                "location" to userLocation
            )
            getTokens()
            userDocRef.update(updates)
            userDocRef.update(updates2)
        }

    }


    fun sendTokens(transaction: Transaction) {
        val currentUserUid = auth.currentUser?.email.toString()
        val tokensToSend = tokensToSendInput
        subtractTokensFromSender(currentUserUid, tokensToSend)
        addTransactionnToSender(transaction)

    }



    fun subtractTokensFromSender(uid: String, tokensToSubtract: Int): Task<Void> {
        db.collection("users").document(recipient.value.toString())
            .update("tokens", FieldValue.increment(tokensToSubtract.toLong()))

        return db.collection("users").document(uid)
            .update("tokens", FieldValue.increment(-tokensToSubtract.toLong()))
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

    fun deleteAccount() {
        val currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser!!.email.toString()).delete()
    }
}