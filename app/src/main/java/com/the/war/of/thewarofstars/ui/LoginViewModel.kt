package com.the.war.of.thewarofstars.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.the.war.of.thewarofstars.base.BaseViewModel
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

    }
}