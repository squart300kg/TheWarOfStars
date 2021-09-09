package com.the.war.of.thewarofstars.model.response

import com.google.gson.annotations.SerializedName

data class PayResponse(

    @SerializedName("gamerCode")
    val gamerCode: Int,

    @SerializedName("userCode")
    val userCode: Int

)
