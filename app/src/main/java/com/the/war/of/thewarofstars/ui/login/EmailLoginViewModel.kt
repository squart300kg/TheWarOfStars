package com.the.war.of.thewarofstars.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.ui.login.EmailLoginActivity.Companion.USER_TYPE

class EmailLoginViewModel(
    private val securePreferences: SecurePreferences
): BaseViewModel(securePreferences) {

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _type = MutableLiveData<String>()
    val type: LiveData<String>
        get() = _type

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _uID = MutableLiveData<String>()
    val uID: LiveData<String>
        get() = _uID

    val TAG = "EmailLoginViewModelLog"

    fun confirmEmailLogin(email: String, password: String, loginType: String) {

        Log.i(TAG, "email: $email, password : $password")

        Application?.instance?.firebaseStore?.collection(loginType)
            ?.whereEqualTo("email", email)
            ?.get()
            ?.addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.i(TAG, "입력 : $password, 서버 : ${document.data["password"]}")
                    if (password == document.data["password"]) {
                        // 로그인 성공
                        _email.value    = email
                        _password.value = password
                        _name.value     = if (loginType == USER_TYPE) document.data["nickname"] as String else document.data["name"] as String
                        _type.value     = if (loginType == USER_TYPE) "USER" else "GAMER"
                        _uID.value      = document.id

                        _isConfirmed.value = true
                        return@addOnSuccessListener
                    } else {
                        // 비밀번호 틀림
                        _isConfirmed.value = false
                        return@addOnSuccessListener
                    }
                }
                // 아이디 틀림
                _isConfirmed.value = false
                return@addOnSuccessListener
            }
    }
}