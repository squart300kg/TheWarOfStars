package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.ReviewItem

class PayCompleteViewModel(
    private val securePreferences: SecurePreferences
): BaseViewModel(securePreferences) {


    private val _isClickedOk = MutableLiveData<Boolean>()
    val isClickedOk: LiveData<Boolean>
        get() = _isClickedOk

    val TAG = "PayCompleteViewModelLog"

    fun clickOK() {
        _isClickedOk.value = true

    }
}
