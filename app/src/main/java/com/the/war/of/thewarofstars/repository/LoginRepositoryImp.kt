package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.BuildConfig
import com.the.war.of.thewarofstars.api.LoginApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImp(
    private val loginApi: LoginApi
): LoginRepository {
    override fun getNaverUserInfo(accessToken: String): Flow<String?> {
        return flow {
            val data = com.the.war.of.thewarofstars.Application.instance?.naverLoginModule?.requestApi(com.the.war.of.thewarofstars.Application.instance?.applicationContext, accessToken, BuildConfig.naverLoginURL)
            emit(data)
        }
    }
}