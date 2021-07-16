package com.the.war.of.thewarofstars.ui

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.the.war.of.thewarofstars.BuildConfig
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.NaverUserResponse
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
                        _errorMsg.value = errorJson.getString("message")
                    }
                    Log.i(TAG, "fail-${_errorCode.value} - ${_errorMsg.value}")
                }
                .collect { naverUserInfoResponse ->
                    Log.i(TAG, naverUserInfoResponse.toString())
                    val response = JSONObject(naverUserInfoResponse).getJSONObject("response")
                    val nickname = response.getString("nickname")
                    val email    = response.getString("email")

                    Log.i(TAG, "nickname : $nickname, email : $email")

                }
        }

    }
}