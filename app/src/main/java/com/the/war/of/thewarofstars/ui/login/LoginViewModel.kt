package com.the.war.of.thewarofstars.ui.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.User
import com.the.war.of.thewarofstars.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel(
    private val loginRepository: LoginRepository
): BaseViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean>
        get() = _isRegister

    val TAG = "LoginViewModelLog"

    fun requestNaverUserInfo(accessToken: String) {
        job?.cancel()
        job = viewModelScope.launch {

            loginRepository.getNaverUserInfo(accessToken)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if(exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value  = errorJson.getString("message")
                    }
                    Log.i(TAG, "fail-${_errorCode.value} - ${_errorMsg.value}")
                }
                .collect { naverUserInfoResponse ->
                    val response    = JSONObject(naverUserInfoResponse).getJSONObject("response")
                    _email.value    = response.getString("email")
                    _nickname.value = response.getString("nickname")

                    Log.i(TAG, "result : ${email.value}, ${nickname.value}")
                }
        }

    }

    fun isRegister(email: String?) {

        Log.i(TAG, "inputEmail : $email")
        Application.instance?.firebaseDB?.collection("UserList")
                ?.whereEqualTo("email", email)
                    ?.get()
                    ?.addOnSuccessListener { documents ->
                        for (document in documents) {
                            _isRegister.value = true
                            return@addOnSuccessListener
                        }
                        _isRegister.value = false
                    }
    }

    fun registerUser() {

        Application.instance?.firebaseDB
            ?.collection("UserList")
            ?.document(email.value.toString())
            ?.set(
                User(
                    _email.value as String,
                    _nickname.value as String
//                "하드코딩 닉네임"
                )
            )
            ?.addOnSuccessListener {
                Log.i(TAG, "회원정보 등록 완료! email : ${_email.value}, nickname : ${_nickname.value}")
                _isRegister.value = true
            }
    }
}