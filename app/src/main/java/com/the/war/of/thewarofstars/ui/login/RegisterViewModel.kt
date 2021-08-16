package com.the.war.of.thewarofstars.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel

/**
 * Created by sangyoon on 2021/08/14
 */
class RegisterViewModel(
    private val securePreferences: SecurePreferences
): BaseViewModel(securePreferences) {

    private val _isExistNickname = MutableLiveData<Boolean>()
    val isExistNickname: LiveData<Boolean>
        get() = _isExistNickname

    private val _isExistEmail = MutableLiveData<Boolean>()
    val isExistEmail: LiveData<Boolean>
        get() = _isExistEmail

    private val TAG = "RegisterViewModelLog"
    fun isExistEmail(email: String) {

        Log.i(TAG, "email : $email")

        Application.instance?.firebaseStore?.collection("UserList")
            ?.whereEqualTo("email", email)
            ?.get()
            ?.addOnSuccessListener { documents ->
                for (document in documents) {
                    if (email == document.data["email"]) {
                        Log.i(TAG, "입력 : $email, 서버 : ${document.data["email"]}")
                        // 동일 닉네임 존재
                        _isExistEmail.value = true
                        Log.i(TAG, "동일 email 존재")
                        return@addOnSuccessListener
                    } else {
                        Log.i(TAG, "입력 : $email, 서버 : ${document.data["email"]}")
                        // 동일 닉네임 없음
                        _isExistEmail.value = false

                        Log.i(TAG, "동일 email 없음1")
                        return@addOnSuccessListener
                    }
                }
                // 동일 닉네임 없음
                Log.i(TAG, "동일 email 없음2")
                _isExistEmail.value = false

                return@addOnSuccessListener
            }
    }
    fun isExistNickname(nickname: String) {

        Log.i(TAG, "nickname : $nickname")

        Application.instance?.firebaseStore?.collection("UserList")
            ?.whereEqualTo("nickname", nickname)
            ?.get()
            ?.addOnSuccessListener { documents ->
                for (document in documents) {
                    if (nickname == document.data["nickname"]) {
                        Log.i(TAG, "입력 : $nickname, 서버 : ${document.data["nickname"]}")
                        // 동일 닉네임 존재
                        _isExistNickname.value = true
                        Log.i(TAG, "동일 닉네임 존재")
                        return@addOnSuccessListener
                    } else {
                        Log.i(TAG, "입력 : $nickname, 서버 : ${document.data["nickname"]}")
                        // 동일 닉네임 없음
                        _isExistNickname.value = false

                        Log.i(TAG, "동일 닉네임 없음1")
                        return@addOnSuccessListener
                    }
                }
                // 동일 닉네임 없음
                Log.i(TAG, "동일 닉네임 없음2")
                _isExistNickname.value = false

                return@addOnSuccessListener
            }
    }

}