package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.PayNotiItem
import com.the.war.of.thewarofstars.model.response.PayResponse
import com.the.war.of.thewarofstars.repository.PayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

/**
 * Created by sangyoon on 2021/08/16
 */
class PayViewModel(
    private val securePreferences: SecurePreferences,
    private val payRepository: PayRepository
) : BaseViewModel(securePreferences) {

    private val _payCompleteDetail = MutableLiveData<PayResponse>()
    val payCompleteDetail: LiveData<PayResponse>
        get() = _payCompleteDetail

    private val TAG = "PayViewModelLog"

    fun sendPayNotification(payNotiItem: PayNotiItem) {
        Log.i(
            TAG,
            "sendPayNotification\n " +
                    "to : ${payNotiItem.to}\n " +
                    "from : ${payNotiItem.from}")

        job?.cancel()
        job = viewModelScope.launch {
            payRepository.sendPayNotification(payNotiItem)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if (exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value = errorJson.getString("message")
                    }
                    exception.printStackTrace()
                    Log.i(TAG,
                        "sendPayNotification\n " +
                                "FAIL\n " +
                                "code : ${_errorCode.value}\n, " +
                                "msg : ${_errorMsg.value}\n," +
                                "exception : $exception")
                }
                .collect {
                    Log.i(TAG, "sendPayNotification\n " +
                            "SUCCESS \n" +
                            "gamerCode : ${it.gamer.gamerCode}\n " +
                            "gamerName : ${it.gamer.gamerName}\n " +
                            "gamerUID : ${it.gamer.gamerUID}\n " +
                            "userUID : ${it.user.userUID}\n " +
                            "userName : ${it.user.userNickname}\n " +
                            "userCode : ${it.user.userCode}")

                    _payCompleteDetail.value = it
                }

        }

    }
}