package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import com.the.war.of.thewarofstars.repository.FreeLectureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class FreeLectureViewModel(
    private val securePreferences: SecurePreferences,
    private val freeLectureRepository: FreeLectureRepository
): BaseViewModel(securePreferences) {

    private val _freeLectureList = MutableLiveData<MutableList<FreeLecturesResponse.Item>>()
    val freeLectureList: LiveData<MutableList<FreeLecturesResponse.Item>>
        get() = _freeLectureList

    private val TAG = "FreeLectureViewModel"

    fun getAllFreeLecture(
        playListId: String,
        apiKey: String
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            _isLoading.value = true
            freeLectureRepository.getFreeLectures(
                playListId = playListId,
                apiKey     = apiKey
            )
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if(exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value = errorJson.getString("message")
                    }
                    Log.i(TAG, "getAllFreeLecture\n " +
                            "FAIL\n " +
                            "code : ${_errorCode.value}\n " +
                            "message : ${_errorMsg.value}")
                }
                .collect {
                    _freeLectureList.value = it.toPresentation().toMutableList()
                    Log.i(TAG, "getAllFreeLecture\n " +
                            "SUCCESS\n " +
                            "result : ${_freeLectureList.value}")
                }

            _isLoading.value = false
        }

    }

}