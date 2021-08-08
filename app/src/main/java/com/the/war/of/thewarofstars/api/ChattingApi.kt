package com.the.war.of.thewarofstars.api

import com.google.firebase.Timestamp
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.NaverUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by sangyoon on 2021/07/27
 */
interface ChattingApi {

    @GET("sendMessage")
    suspend fun sendMessage(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("content") content: String
    ): ChattingResponse
}