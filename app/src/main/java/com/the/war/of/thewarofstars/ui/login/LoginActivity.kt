package com.the.war.of.thewarofstars.ui.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding
import com.the.war.of.thewarofstars.util.NaverLogin
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {


    private val loginViewModel: LoginViewModel by viewModel()

    val TAG = "LoginActivityLog"

    private val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result?.resultCode == Activity.RESULT_OK) {
            goNext(MainActivity::class.java)
        }
    }

    /**
     * 로그인 프로세스
     *1. 네이버 서버로부터 사용자 정보를 가져온다.
     *2. 사용자 정보를 사용해 회원가입 여부를 검사한다.
     * 2.1. 회원가입을 했다면 메인페이지로 넘겨준다.
     * 2.2. 회원가입을 안했다면 서버에 회원정보를 저장하고 메인페이지로 넘겨준다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.daum_star_league))
            videoView.start()

            naverLoginButton.apply {
                setOnClickListener { NaverLogin.startNaverLogin(this@LoginActivity, loginViewModel) }
            }
            emailLoginButton.apply {
                setOnClickListener {
                    startActivityResult.launch(Intent(this@LoginActivity, EmailLoginActivity::class.java))
                }
            }
            videoView.apply {
                setOnClickListener{ NaverLogin.startNaverLogout(this@LoginActivity) }
            }
        }

        observing {
            email.observe(this@LoginActivity, { email ->
                Log.i(TAG, "observe email : $email")

            })

            nickname.observe(this@LoginActivity, { nickname ->
                Log.i(TAG, "observe nickname : $nickname")

            })

            naverApiStatus.observe(this@LoginActivity, { status ->
                when (status) {
                    true -> {
                        loginViewModel.isRegister(loginViewModel.email.value)
                    }
                    false -> { }
                }
            })

            isRegister.observe(this@LoginActivity, { isRegister ->
                when (isRegister) {
                    true -> {
                        /**
                         * 회원이므로 메인페이지로 이동한다.
                         */
                        Application.instance?.userEmail    = loginViewModel.email.value
                        Application.instance?.userNickname = loginViewModel.nickname.value
                        goNext(MainActivity::class.java)
                    }
                    false -> {
                        /**
                         * 회원이 아니므로, 회원가입 후, 메인페이지로 이동한다.
                         */
                        loginViewModel.registerUser()
                    }
                }
            })
        }

    }

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



