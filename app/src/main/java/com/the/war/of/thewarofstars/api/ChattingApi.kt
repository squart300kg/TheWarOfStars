package com.the.war.of.thewarofstars.api

import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.NaverUserResponse
import retrofit2.http.Body
import retrofit2.http.GET

/**
 * Created by sangyoon on 2021/07/27
 */
interface ChattingApi {

    @GET("sendMessage")
    suspend fun sendMessage(@Body chattingItem: ChattingItem): ChattingResponse
}