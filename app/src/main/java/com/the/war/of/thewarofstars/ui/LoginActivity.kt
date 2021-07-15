package com.the.war.of.thewarofstars.ui

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLoginBinding

    val TAG = "LoginActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.lifecycleOwner = this

        binding {
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.daum_star_league))
            videoView.start()

            naverLoginButton.setOnClickListener { startNaverLogin() }
            videoView.setOnClickListener{ startNaverLogout() }
        }

    }
    private fun startNaverLogout() = Application.instance?.naverLoginModule?.logoutAndDeleteToken(this@LoginActivity)

    private fun startNaverLogin() =
        Application.instance?.naverLoginModule?.startOauthLoginActivity(this@LoginActivity, naverLoginListener())

    private fun naverLoginListener() = object: OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val naverLoginModule = Application.instance?.naverLoginModule
                val accessToken = naverLoginModule?.getAccessToken(this@LoginActivity)
                val refreshToken = naverLoginModule?.getRefreshToken(this@LoginActivity)
                val result = naverLoginModule?.requestApi(this@LoginActivity, accessToken, "https://openapi.naver.com/v1/nid/me")



                Log.i(TAG, "accessToken : $accessToken")
                Log.i(TAG, "refreshToken : $refreshToken")
                Log.i(TAG, "result : $result")
            } else {

            }
        }

    }

    // 로그인 프로세스
    // 1. 카카로 서버로부터 사용자 정보를 가져온다.
    // 2. 사용자 정보를 사용해 회원가입 여부를 검사한다.
    //   2.1. 회원가입을 했다면 메인페이지로 넘겨준다.
    //   2.2. 회원가입을 안했다면 서버에 회원정보를 저장하고 메인페이지로 넘겨준다.


    private fun binding(action: ActivityLoginBinding.() -> Unit) {
        dataBinding.run(action)
    }

    private fun goNext(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}