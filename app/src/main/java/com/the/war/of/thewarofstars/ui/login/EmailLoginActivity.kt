package com.the.war.of.thewarofstars.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityEmailLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailLoginActivity: BaseActivity<ActivityEmailLoginBinding>(R.layout.activity_email_login) {

    private val emailLoginViewModel: EmailLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            btnLogin.setOnClickListener {
                emailLoginViewModel.confirmEmailLogin(
                    etvEmail.text.toString(),
                    etvPassword.text.toString(),
                )
            }
        }
        observing {
            isConfirmed.observe(this@EmailLoginActivity, { isConfirmed ->
                when (isConfirmed) {
                    true -> {
                        setResult(RESULT_OK)
                        finish()
                    }
                    false -> {
                        showToast("아이디 또는 비밀번호가 일치하지 않습니다.")
                    }
                }

            } )
        }


    }

    private fun observing(action: EmailLoginViewModel.() -> Unit) {
        emailLoginViewModel.run(action)
    }
}