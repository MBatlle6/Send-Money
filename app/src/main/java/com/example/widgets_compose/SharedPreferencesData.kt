package com.example.widgets_compose

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec



class SharedPreferencesData private constructor(context: Context) {

    private val sharedPreferencesConections: SharedPreferences = context.getSharedPreferences("connections", Context.MODE_PRIVATE)
    private val sharedPreferencesShowTokens: SharedPreferences = context.getSharedPreferences("showTokens", Context.MODE_PRIVATE)


//    private fun encrypt(input: String): String {
//        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
//        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//        val encryptedBytes = cipher.doFinal(input.toByteArray())
//        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
//    }
//
//    private fun decrypt(input: String): String {
//        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
//        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
//        cipher.init(Cipher.DECRYPT_MODE, secretKey)
//        val encryptedBytes = Base64.decode(input, Base64.DEFAULT)
//        val decryptedBytes = cipher.doFinal(encryptedBytes)
//        return String(decryptedBytes)
//    }


    fun showTokens(show: Boolean){
        sharedPreferencesShowTokens.edit().putBoolean("showTokens", show).apply()
    }

    fun getShowTokens(): Boolean {
        return sharedPreferencesShowTokens.getBoolean("showTokens", true)
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