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

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

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
        val name     = securePreferences.getEncryptedString("name", "")
        val uID      = securePreferences.getEncryptedString("uID", "")
        val type     = securePreferences.getEncryptedString("type", "")
        val tribe    = securePreferences.getEncryptedString("tribe", "")
        val gameID   = securePreferences.getEncryptedString("gameID", "")


        val map = HashMap<String, String>()
        map["email"]    = email
        map["password"] = password
        map["name"]     = name
        map["uID"]      = uID
        map["type"]     = type
        map["tribe"]    = tribe
        map["gameID"]   = gameID

        return map
    }

    fun saveAutoLogin(
        autoLogin: Boolean,
        email: String?,
        name: String?,
        uID: String?,
        password: String?,
        type: String?,
        tribe: String?,
        gameID: String?
    ) {
        securePreferences.edit().putUnencryptedString("autoLoginStatus", autoLogin.toString()).commit()
        securePreferences.edit().putUnencryptedString("email", email.toString()).commit()
        securePreferences.edit().putUnencryptedString("name", name.toString()).commit()
        securePreferences.edit().putUnencryptedString("uID", uID.toString()).commit()
        securePreferences.edit().putUnencryptedString("password", password.toString()).commit()
        securePreferences.edit().putUnencryptedString("type", type.toString()).commit()
        securePreferences.edit().putUnencryptedString("tribe", tribe.toString()).commit()
        securePreferences.edit().putUnencryptedString("gameID", gameID.toString()).commit()
    }

    fun deleteAutoLogin() {
        securePreferences.edit().putUnencryptedString("autoLoginStatus", "false").commit()
        securePreferences.edit().putUnencryptedString("email", null).commit()
        securePreferences.edit().putUnencryptedString("name", null).commit()
        securePreferences.edit().putUnencryptedString("uID", null).commit()
        securePreferences.edit().putUnencryptedString("password", null).commit()
        securePreferences.edit().putUnencryptedString("type", null).commit()
        securePreferences.edit().putUnencryptedString("tribe", null).commit()
        securePreferences.edit().putUnencryptedString("gameID", null).commit()
    }
}