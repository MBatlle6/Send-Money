package com.example.widgets_compose

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec



class SharedPreferencesData private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("tokens", Context.MODE_PRIVATE)
    private val sharedPreferencesTranscations: SharedPreferences = context.getSharedPreferences("transactions", Context.MODE_PRIVATE)
    private val sharedPreferencesConections: SharedPreferences = context.getSharedPreferences("connections", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "rK4!T[+!m}2x@e6Z3Fz9nQV5wA7p*Ju" // Clave de 256 bits (32 bytes) en formato String
    private val  allowedConnections = MutableLiveData(true)

    fun saveTokens(amount: Int, values: MutableList<Transaction>) {
        val currentTokens = sharedPreferences.getInt(TOKENS_KEY, 0)
        val newTokens = currentTokens + amount // Sumar el valor actual con el nuevo valor
        sharedPreferences.edit().putInt(TOKENS_KEY, newTokens).apply()
        val json = gson.toJson(values)
        sharedPreferencesTranscations.edit().putString("transactions", json).apply()
    }



    fun getTransactions(values: MutableList<Transaction>): MutableList<Transaction> {
        val json = sharedPreferencesTranscations.getString("transactions", "")
        return if (json != "") {
            gson.fromJson(json, Array<Transaction>::class.java).toMutableList()
        } else {
            mutableListOf()
        }
    }


    private fun encrypt(input: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    private fun decrypt(input: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val encryptedBytes = Base64.decode(input, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    fun sendTokens(amount: Int) {
        val currentTokens = sharedPreferences.getInt(TOKENS_KEY, 0) // Obtener el valor actual de tokens
        val newTokens = currentTokens - amount // Restar el valor actual con el nuevo valor
        sharedPreferences.edit().putInt(TOKENS_KEY, newTokens).apply()
    }

    fun setConnections(allow: Boolean){
        sharedPreferencesConections.edit().putBoolean("connections", allow).apply()
    }

    fun getConnections(): Boolean {
        return sharedPreferencesConections.getBoolean("connections", true)
    }


    companion object {
        private const val TOKENS_KEY = "tokens"
        @Volatile
        private var instance: SharedPreferencesData? = null

        fun getInstance(context: Context): SharedPreferencesData {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesData(context).also { instance = it }
            }
        }
    }
}