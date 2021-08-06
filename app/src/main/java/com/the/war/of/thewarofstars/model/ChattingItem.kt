package com.the.war.of.thewarofstars.model

import com.google.firebase.Timestamp

data class ChattingItem(
    val uID: String? = null,
    val to: String,
    val from: String,
    val content: String,
    val currentTime: Timestamp,
    val isHost: Boolean
)
