package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.model.Gamer

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment!!!!"
    }
    val text: LiveData<String> = _text

    private val _gamerList = MutableLiveData<Gamer>()
    val gamerList
        get() = _gamerList

    private val TAG = "HomeViewModelLog"

    fun getGamerList() {
        val db = Application?.instance?.firebaseDB

    }
}