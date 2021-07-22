package com.the.war.of.thewarofstars.model

data class Gamer(
    val name: String,
    val price: Long,
    val title: String,
    val thumbnailURL: String,
    val description: String,
    val review_list: Review?
)
