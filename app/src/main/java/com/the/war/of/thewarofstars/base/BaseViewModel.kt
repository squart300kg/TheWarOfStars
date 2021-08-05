package com.the.war.of.thewarofstars.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

open class BaseViewModel(
    private val securePreferences: SecurePreferences
): ViewModel() {

    protected val _errorCode = MutableLiveData<String>()
    val errorCode: LiveData<String>
        get() = _errorCode

    protected val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String>
        get() = _errorMsg

    protected var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun getAutoLoginStatus(): String {
        return securePreferences.getEncryptedString("autoLoginStatus", "")
    }

    fun getAutoLoginUserInfo(): Map<String, String> {
        val email    = securePreferences.getEncryptedString("email", "")
        val password = securePreferences.getEncryptedString("password", "")

        val map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password

        return map
    }

    fun saveAutoLogin(autoLogin: Boolean, email: String?, password: String?) {
        securePreferences.edit().putUnencryptedString("autoLoginStatus", autoLogin.toString()).commit()
        securePreferences.edit().putUnencryptedString("email", email.toString()).commit()
        securePreferences.edit().putUnencryptedString("password", password.toString()).commit()
    }
}