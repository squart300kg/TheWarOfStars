package com.the.war.of.thewarofstars.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun getNaverUserInfo(accessToken: String): Flow<String?>

}