package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.repository.ChattingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

/**
 * Created by sangyoon on 2021/07/26
 */
class QuestionViewModel(
    private val chattingRepository: ChattingRepository
): BaseViewModel() {

    private val TAG = "QuestionViewModelLog"

    fun sendMessage(
        chattingItem: ChattingItem
    )
//    : Task<String>
    {

        Log.i(TAG, "chattingItem : $chattingItem")

//        val item = hashMapOf(
//            "text" to chattingItem.content
////            "from" to chattingItem.from,
////            "content" to chattingItem.content,
////            "currentTime" to chattingItem.currentTime
//        )

//        return requireNotNull(Application.instance?.fireFunction)
//            .getHttpsCallable("sendMessage")
//            .call(item)
//            .continueWith { task ->
//                // This continuation runs on either success or failure, but if the task
//                // has failed then result will throw an Exception which will be
//                // propagated down.
//                val result = task.result?.data as String
//                result
//            }
//            .addOnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    val e = task.exception
//                    if (e is FirebaseFunctionsException) {
//                        val code = e.code
//                        val details = e.details
//                        Log.i(TAG, "code : $code, details : $details")
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Log.i(TAG, "failed - $it")
//            }
//            .addOnSuccessListener {
//                Log.i(TAG, "successed - $it")
//            }

        job?.cancel()
        job = viewModelScope.launch {
            chattingRepository.sendMessage(chattingItem)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if (exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value = errorJson.getString("message")
                    }
                    Log.i(TAG, "failed - code : ${_errorCode.value}, msg : ${_errorMsg.value}, exception : $exception")
                }
                .collect {
                    Log.i(TAG, "result : ${it.result}")
                }

        }
    }

}