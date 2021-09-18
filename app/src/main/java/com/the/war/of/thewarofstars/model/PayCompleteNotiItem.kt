package com.the.war.of.thewarofstars.model

import com.google.gson.annotations.SerializedName

data class PayCompleteNotiItem(

    @SerializedName("gamer")
    val gamer: Gamer,

    @SerializedName("user")
    val user: User,

    @SerializedName("price")
    val price: String,

) {
    data class User(
        @SerializedName("userUID")
        val userUID: String,

        @SerializedName("userNickname")
        val userNickname: String,

        @SerializedName("userCode")
        val userCode: String,

        @SerializedName("userTribe")
        val userTribe: String,

        @SerializedName("userID")
        val userID: String,
    )

    data class Gamer(
        @SerializedName("gamerUID")
        val gamerUID: String,

        @SerializedName("gamerName")
        val gamerName: String,

        @SerializedName("gamerCode")
        val gamerCode: String,

        @SerializedName("gamerTribe")
        val gamerTribe: String,

        @SerializedName("gamerID")
        val gamerID: String,
    )
}
