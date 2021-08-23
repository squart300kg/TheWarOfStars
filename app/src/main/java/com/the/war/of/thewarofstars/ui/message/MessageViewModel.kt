package com.the.war.of.thewarofstars.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.ChattingListItem
import com.the.war.of.thewarofstars.model.GamerItem

class MessageViewModel : ViewModel() {
    private val _chattingList = MutableLiveData<MutableList<ChattingListItem>>()

    val chattingList: LiveData<MutableList<ChattingListItem>>
        get() = _chattingList

    fun getChattingList() {

    }

}