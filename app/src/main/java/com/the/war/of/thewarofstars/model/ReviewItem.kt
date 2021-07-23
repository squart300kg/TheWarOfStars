package com.the.war.of.thewarofstars.model

import com.google.firebase.Timestamp

data class ReviewItem(
    val content: String,
    val date: Timestamp,
    val gamer: String,
    val nickname: String,
    val score: Long
)
