package com.the.war.of.thewarofstars.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityEmailLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailLoginActivity: BaseActivity<ActivityEmailLoginBinding>(R.layout.activity_email_login) {

    private val emailLoginViewModel: EmailLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            etvEmail.apply {
                setOnDeleteButton()
            }

            etvPassword.apply {
                setOnDeleteButton()
            }

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

                        val email    = emailLoginViewModel.email.value
                        val password = emailLoginViewModel.password.value

                        saveAutoLogin(true, email, password)

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

    @SuppressLint("ClickableViewAccessibility")
    private fun EditText.setOnDeleteButton() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0) {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                } else {
                    val img = R.mipmap.icon_close_white
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, img, 0)
                }

                checkLoginButtonEnable()
            }
        })

        // x버튼을 누르면 클리어된다.
        setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_UP) {
                    if (compoundDrawables[DRAWABLE_RIGHT] != null) {
                        if (event.rawX >= (right - compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                            // your action here
                            text?.clear()

                            checkLoginButtonEnable()

                            return false
                        }
                    }
                }
                return false
            }
        })
    }

    private fun checkLoginButtonEnable() {
        val email = dataBinding.etvEmail.text.toString()
        val password = dataBinding.etvPassword.text.toString()

        dataBinding.btnLogin.isSelected = email.isNotEmpty()
                && password.isNotEmpty()
//                && password.length >= 8
    }


    companion object {
        const val DRAWABLE_RIGHT = 2
    }

}