package com.the.war.of.thewarofstars.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityEmailLoginBinding

class EmailLoginActivity: BaseActivity<ActivityEmailLoginBinding>(R.layout.activity_email_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            btnLogin.setOnClickListener {  }
        }


    }
}