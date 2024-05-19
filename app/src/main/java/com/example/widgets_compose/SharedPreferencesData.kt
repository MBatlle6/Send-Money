package com.example.widgets_compose

import android.content.Context
import android.content.SharedPreferences
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64



class SharedPreferencesData private constructor(context: Context) {

    private val sharedPreferencesConections: SharedPreferences = context.getSharedPreferences("connections", Context.MODE_PRIVATE)
    private val sharedPreferencesShowTokens: SharedPreferences = context.getSharedPreferences("showTokens", Context.MODE_PRIVATE)


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