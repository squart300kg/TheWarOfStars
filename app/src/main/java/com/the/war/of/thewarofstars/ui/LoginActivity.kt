package com.the.war.of.thewarofstars.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLoginBinding
    private lateinit var onPreparedListener: MediaPlayer.OnPreparedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.lifecycleOwner = this

        onPreparedListener = MediaPlayer.OnPreparedListener {
            it.isLooping = true
        }

        binding {
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.mcdonalds))
            videoView.start()
            videoView.setOnPreparedListener(onPreparedListener)
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