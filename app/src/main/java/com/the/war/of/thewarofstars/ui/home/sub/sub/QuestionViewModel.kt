package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.R.attr.button
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
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
    private val securePreferences: SecurePreferences,
    private val chattingRepository: ChattingRepository
): BaseViewModel(securePreferences) {

    private val _chattingHistory = MutableLiveData<MutableList<ChattingItem>>()
    val chattingHistory: LiveData<MutableList<ChattingItem>>
        get() = _chattingHistory

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

    /**
     * 채팅내역 가져오기 프로세스
     * 1. 나의 uID와 상대방 uID를 이용해 채팅리스트를 조회한다.
     */
    fun loadChattingHistory(sender: String, receiver: String) {
        Log.i(TAG, "sender : $sender, receiver : $receiver")
        Application.instance?.firebaseDatabase
            ?.reference
            ?.child("user")
            ?.child(sender)
            ?.child(receiver)
            ?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                /**
                 * 채팅내역 불러오기 프로세스
                 * 1. sender가 receiver와 채팅하고 있는 내역을 모두 불러온다.
                 * 2. commentUid를 통해 comment데이터를 가져온다
                 */
                snapshot.children.forEach {
                    Log.i(TAG, "key : ${it.key}, value : ${it.value}")

                    val commentUID = it.key
                    val isHost     = it.value

                    getCommentContent(commentUID)

//                    Application.instance?.firebaseDatabase
//                        ?.getReference("comment/$commentUID")
//                        ?.addValueEventListener(object: ValueEventListener {
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                snapshot.children.forEach {
//                                    Log.i(TAG, "key : ${it.key}, value : ${it.value}")
//                                }
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//
//                            }
//                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })






    }

    private fun getCommentContent(commentUID: String?) {


    }

}