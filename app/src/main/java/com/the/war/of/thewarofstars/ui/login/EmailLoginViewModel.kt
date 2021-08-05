package com.the.war.of.thewarofstars.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel

class EmailLoginViewModel(
    private val securePreferences: SecurePreferences
): BaseViewModel(securePreferences) {

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    val TAG = "EmailLoginViewModelLog"

    fun confirmEmailLogin(email: String, password: String) {

        Log.i(TAG, "email: $email, password : $password")

        Application?.instance?.firebaseStore?.collection("UserList")
            ?.whereEqualTo("email", email)
            ?.get()
            ?.addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.i(TAG, "입력 : $password, 서버 : ${document.data["password"]}")
                    if (password == document.data["password"]) {
                        // 로그인 성공
                        _email.value    = email
                        _password.value = password

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