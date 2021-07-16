package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.model.NaverUserResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun getNaverUserInfo(accessToken: String): Flow<String?>

}