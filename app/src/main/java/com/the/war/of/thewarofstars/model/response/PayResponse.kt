package com.the.war.of.thewarofstars.model.response

import com.google.gson.annotations.SerializedName

data class PayResponse(

    @SerializedName("user")
    val user: User,

    @SerializedName("gamer")
    val gamer: Gamer

) {
    data class User(
        @SerializedName("userUID")
        val userUID: String,

        @SerializedName("userCode")
        val userCode: String,
    )

    data class Gamer(
        @SerializedName("gamerUID")
        val gamerUID: String,

        @SerializedName("gamerCode")
        val gamerCode: String,
    )
}
