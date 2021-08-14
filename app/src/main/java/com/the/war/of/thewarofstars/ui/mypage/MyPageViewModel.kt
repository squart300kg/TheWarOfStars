package com.the.war.of.thewarofstars.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import org.koin.java.KoinJavaComponent.inject

class MyPageViewModel: ViewModel() {

    val securePreferences: SecurePreferences by inject(SecurePreferences::class.java)

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val TAG = "MyPageViewModelLog"

    fun logout() {

        Log.i(TAG, "logout")

        _isConfirmed.value = true
    }

    fun deleteAutoLogin() {
        securePreferences.edit().putUnencryptedString("autoLoginStatus", "false").commit()
        securePreferences.edit().putUnencryptedString("email", null).commit()
        securePreferences.edit().putUnencryptedString("name", null).commit()
        securePreferences.edit().putUnencryptedString("uID", null).commit()
        securePreferences.edit().putUnencryptedString("password", null).commit()
        securePreferences.edit().putUnencryptedString("type", null).commit()
    }
}