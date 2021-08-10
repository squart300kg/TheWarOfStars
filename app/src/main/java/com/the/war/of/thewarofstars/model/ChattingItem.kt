package com.the.war.of.thewarofstars.model

data class ChattingItem(
    val uid: String? = null,
    val to: String? = null,
    val from: String? = null,
    val content: String? = null,
    val timeStamp: Long = System.currentTimeMillis(),
    val type: String?= null
)
