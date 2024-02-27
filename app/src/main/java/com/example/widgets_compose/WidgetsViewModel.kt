package com.example.widgets_compose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class WidgetsViewModel: ViewModel() {


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
    val transactions = MutableLiveData<List<Transaction>>(listOf(
        Transaction("Yo", "Juan", LocalDate.now(), 100.0),
        Transaction("Pedro", "Yo", LocalDate.now(), 50.0),
        Transaction("Yo", "María", LocalDate.now(), 75.0),
        Transaction("Yo", "Luis", LocalDate.now(), 120.0),
        Transaction("Él", "Yo", LocalDate.now(), 200.0),
        Transaction("Yo", "Juan", LocalDate.now(), 100.0),
        Transaction("Pedro", "Yo", LocalDate.now(), 50.0),
        Transaction("Yo", "María", LocalDate.now(), 75.0),
        Transaction("Yo", "Luis", LocalDate.now(), 120.0),
        Transaction("Él", "Yo", LocalDate.now(), 200.0),
        Transaction("Yo", "Juan", LocalDate.now(), 100.0),
        Transaction("Pedro", "Yo", LocalDate.now(), 50.0),
        Transaction("Yo", "María", LocalDate.now(), 75.0),
        Transaction("Yo", "Luis", LocalDate.now(), 120.0),
    ))


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