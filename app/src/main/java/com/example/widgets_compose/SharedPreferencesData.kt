package com.example.widgets_compose

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class SharedPreferencesData private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("tokens", Context.MODE_PRIVATE)
    private val sharedPreferencesTranscations: SharedPreferences = context.getSharedPreferences("transactions", Context.MODE_PRIVATE)
    private val gson = Gson()
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
    fun getTokens(): Int {
        return sharedPreferences.getInt(TOKENS_KEY, 0) // Devuelve 0 si no hay ningún valor guardado previamente
    }



    fun sendTokens(amount: Int) {
        val currentTokens = sharedPreferences.getInt(TOKENS_KEY, 0) // Obtener el valor actual de tokens
        val newTokens = currentTokens - amount // Restar el valor actual con el nuevo valor
        sharedPreferences.edit().putInt(TOKENS_KEY, newTokens).apply()

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