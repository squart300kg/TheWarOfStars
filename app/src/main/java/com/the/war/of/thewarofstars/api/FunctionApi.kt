package com.the.war.of.thewarofstars.api

import com.google.firebase.Timestamp
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.PayCompleteNotiItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.NaverUserResponse
import com.the.war.of.thewarofstars.model.response.PayCompleteResponse
import com.the.war.of.thewarofstars.model.response.PayResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by sangyoon on 2021/07/27
 */
interface FunctionApi {

    @GET("sendMessage")
    suspend fun sendMessage(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("content") content: String,
        @Query("type") type: String
    ): ChattingResponse

    @GET("sendPayNotification")
    suspend fun sendPayNotification(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("content") content: String,
        @Query("price") price: String,
    ): PayResponse

    @GET("sendPayCompleteNotification")
    suspend fun sendPayCompleteNotification(
        @Body payCompleteNotiItem: PayCompleteNotiItem
    ): PayCompleteResponse


}