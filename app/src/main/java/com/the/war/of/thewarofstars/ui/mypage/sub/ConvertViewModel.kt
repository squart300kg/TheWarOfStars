package com.the.war.of.thewarofstars.ui.mypage.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securepreferences.SecurePreferences
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.java.KoinJavaComponent.inject

class ConvertViewModel: ViewModel() {
    val securePreferences: SecurePreferences by inject(SecurePreferences::class.java)

    private val _isConvertClicked = MutableLiveData<Boolean>()
    val isConvertClicked: LiveData<Boolean>
        get() = _isConvertClicked

    private val TAG = "MyPageViewModelLog"

    fun convertClick() {
        _isConvertClicked.value = true
    }
}
