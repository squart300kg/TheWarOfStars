package com.the.war.of.thewarofstars.ui.home.sub.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private val _senderUID = MutableLiveData<String>()
    val senderUID: LiveData<String>
        get() = _senderUID

    private val _receiverUID = MutableLiveData<String>()
    val receiverUID: LiveData<String>
        get() = _receiverUID

    private val TAG = "QuestionViewModelLog"

    fun sendMessage(chattingItem: ChattingItem){

        Log.i(TAG, "chattingItem : $chattingItem")

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
                    exception.printStackTrace()
                    Log.i(TAG, "failed - code : ${_errorCode.value}, msg : ${_errorMsg.value}, exception : $exception")
                }
                .collect {
                    Log.i(TAG, "commentDate : ${it.commentDate}")
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
            ?.child("users")
            ?.child(sender)
            ?.child("ChattingRooms")
            ?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                /**
                 * 채팅내역 불러오기 프로세스
                 * 1. 채팅방 내역을 모두 불러온다.
                 * 2. 채팅방 리스트에서 대화중인 사용자가 지금 대화하려는 사용자와 같을 때, 채팅방 id를 가져온다.
                 * 3. 채팅방 id에 해당하는 comments를 모두 불러온다.
                 */
                snapshot.children.forEach {

                    val roomId = it.key
                    val userId = it.value

                    if (userId == receiver) {
                        getCommentContent(roomId.toString())

                        _senderUID.value   = sender
                        _receiverUID.value = receiver
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }

    private fun getCommentContent(roomId: String) {
        Application.instance?.firebaseDatabase
            ?.reference
            ?.child("ChattingRooms")
            ?.child(roomId)
            ?.child("comments")
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    /**
                     * 채팅내역 불러오기 프로세스
                     * 1. 채팅방 내역을 모두 불러온다.
                     * 2. 채팅방 리스트에서 대화중인 사용자가 지금 대화하려는 사용자와 같을 때, 채팅방 id를 가져온다.
                     * 3. 채팅방 id에 해당하는 comments를 모두 불러온다.
                     */
                    val chattingHistory = mutableListOf<ChattingItem>()
                    snapshot.children.forEach {

                        val commentUid     = it.key
                        val commentContent = it.getValue(ChattingItem().javaClass)

                        val commentHostUid = commentContent?.uid
                        val timeStamp      = commentContent?.timeStamp
                        val content        = commentContent?.content

                        Log.i(TAG, "timeStamp : $timeStamp, uid : $commentHostUid, content : $content")

                        val chattingItem = ChattingItem(
                            uid = commentHostUid,
                            timeStamp = requireNotNull(timeStamp),
                            content = content.toString()
                        )
                        chattingHistory.add(chattingItem)
                    }
                    _chattingHistory.value = chattingHistory
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })

    }

}