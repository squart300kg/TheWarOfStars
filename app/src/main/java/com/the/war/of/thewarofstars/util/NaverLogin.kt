package com.the.war.of.thewarofstars.util

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.ui.login.LoginViewModel

object NaverLogin {
    fun startNaverLogout(activity: Activity) =
        Application.instance?.naverLoginModule?.logoutAndDeleteToken(activity)

    fun startNaverLogin(activity: Activity, loginViewModel: LoginViewModel) =
        Application.instance?.naverLoginModule?.startOauthLoginActivity(activity, naverLoginListener(activity, loginViewModel))

    fun naverLoginListener(activity: Activity, loginViewModel: LoginViewModel) = object: OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val naverLoginModule = Application.instance?.naverLoginModule
                val accessToken = naverLoginModule?.getAccessToken(activity)
                val refreshToken = naverLoginModule?.getRefreshToken(activity)

                accessToken?.let { loginViewModel.requestNaverUserInfo(it) }

                Log.i(TAG, "accessToken : $accessToken")
                Log.i(TAG, "refreshToken : $refreshToken")
            } else {

            }
        }

    }
}