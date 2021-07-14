package com.the.war.of.thewarofstars.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kakao.sdk.user.UserApiClient
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLoginBinding
    private lateinit var onPreparedListener: MediaPlayer.OnPreparedListener

    val TAG = "LoginActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.lifecycleOwner = this

        binding {
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.daum_star_league))
            videoView.start()

            kakaoLoginButton.setOnClickListener { kakaoLoginListener() }
        }

    }

    private fun kakaoLoginListener() = UserApiClient.instance.loginWithKakaoTalk(this) { token ,error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
        }
        else if (token != null) {
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
        }
    }

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