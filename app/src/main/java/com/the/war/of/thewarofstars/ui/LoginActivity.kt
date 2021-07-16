package com.the.war.of.thewarofstars.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding
import com.the.war.of.thewarofstars.util.NaverLogin
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {


    private val loginViewModel: LoginViewModel by viewModel()

    val TAG = "LoginActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.daum_star_league))
            videoView.start()

            naverLoginButton.setOnClickListener { NaverLogin.startNaverLogin(this@LoginActivity, loginViewModel) }
            videoView.setOnClickListener{ NaverLogin.startNaverLogout(this@LoginActivity) }
        }

        observing {
            email.observe(this@LoginActivity, { email ->
                Log.i(TAG, "observe email : $email")
                loginViewModel.isRegister(email)
            })
        }

    }
    // 로그인 프로세스
    // 1. ㄴㅔ이버 서버로부터 사용자 정보를 가져온다.
    // 2. 사용자 정보를 사용해 회원가입 여부를 검사한다.
    //   2.1. 회원가입을 했다면 메인페이지로 넘겨준다.
    //   2.2. 회원가입을 안했다면 서버에 회원정보를 저장하고 메인페이지로 넘겨준다.

    private fun observing(action: LoginViewModel.() -> Unit) {
        loginViewModel.run(action)
    }
    private fun goNext(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }



}



