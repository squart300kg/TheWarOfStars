package com.the.war.of.thewarofstars.ui.home.sub.pay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.base.BaseViewModel

/**
 * Created by sangyoon on 2021/08/16
 */
class PayViewModel(
    private val securePreferences: SecurePreferences
) : BaseViewModel(securePreferences) {

    private val _reviewCount = MutableLiveData<Int>()
    val reviewCount: LiveData<Int>
        get() = _reviewCount

    fun hello() {

    }
}