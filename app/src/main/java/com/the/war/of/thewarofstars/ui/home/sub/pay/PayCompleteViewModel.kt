package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.PayCompleteNotiItem
import com.the.war.of.thewarofstars.model.ReviewItem
import com.the.war.of.thewarofstars.model.response.PayCompleteResponse
import com.the.war.of.thewarofstars.repository.PayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class PayCompleteViewModel(
    private val securePreferences: SecurePreferences,
    private val payRepository: PayRepository
): BaseViewModel(securePreferences) {


    private val _isClickedOk = MutableLiveData<Boolean>()
    val isClickedOk: LiveData<Boolean>
        get() = _isClickedOk

    private val _payCompleteDetail = MutableLiveData<PayCompleteResponse>()
    val payCompleteDetail: LiveData<PayCompleteResponse>
        get() = _payCompleteDetail

    val TAG = "PayCompleteViewModelLog"

    fun clickOK() {
        _isClickedOk.value = true
    }

    fun sendPayCompleteNotificationWithPayUID(payUID: String) {
        job?.cancel()
        job = viewModelScope.launch {
            payRepository.sendPayCompleteNotification(payUID)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if (exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value = errorJson.getString("message")
                    }
                    exception.printStackTrace()
                    Log.i(
                        TAG,
                        "sendPayCompleteNotification\n " +
                                "FAIL\n " +
                                "code : ${_errorCode.value}\n, " +
                                "msg : ${_errorMsg.value}\n," +
                                "exception : $exception")
                }
                .collect {
                    Log.i(
                        TAG,
                        "sendPayCompleteNotification\n " +
                                "SUCCESS \n" +
                                "gamerCode : ${it.gamer.gamerCode}\n " +
                                "gamerName : ${it.gamer.gamerName}\n " +
                                "gamerUID : ${it.gamer.gamerUID}\n " +
                                "userUID : ${it.user.userUID}\n " +
                                "userName : ${it.user.userNickname}\n " +
                                "userCode : ${it.user.userCode}\n" +
                                "price : ${it.price}\n" +
                                "payDate : ${it.payDate}\n" +
                                "payStatus : ${it.payStatus}\n" +
                                "payUID : ${it.payUID}\n" +
                                "notiType : ${it.notiType}\n"
                    )

                    _payCompleteDetail.value = it
                }
        }
    }
}
