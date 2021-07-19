package com.the.war.of.thewarofstars.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.base.BaseViewModel

class EmailLoginViewModel: BaseViewModel() {

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    val TAG = "EmailLoginViewModelLog"

    fun confirmEmailLogin(email: String, password: String) {

        Log.i(TAG, "email: $email, password : $password")

        Application?.instance?.firebaseDB?.collection("UserList")
            ?.whereEqualTo("email", email)
            ?.get()
            ?.addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.i(TAG, "입력 : $password, 서버 : ${document.data["password"]}")
                    if (password == document.data["password"]) {
                        // 로그인 성공
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