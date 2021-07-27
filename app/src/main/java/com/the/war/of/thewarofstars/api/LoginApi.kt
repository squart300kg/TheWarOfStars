package com.the.war.of.thewarofstars.api

import com.the.war.of.thewarofstars.model.response.NaverUserResponse
import retrofit2.http.GET

interface LoginApi {

    @GET("v1/nid/me")
    suspend fun getNaverUserInfo(): NaverUserResponse

}