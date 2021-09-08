package com.the.war.of.thewarofstars.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.contant.CollectionType
import com.the.war.of.thewarofstars.databinding.ActivityEmailLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailLoginActivity: BaseActivity<ActivityEmailLoginBinding>(R.layout.activity_email_login) {

    private val emailLoginViewModel: EmailLoginViewModel by viewModel()

    private var loginType = CollectionType.GAMER_LIST.type

    private val TAG = "EmailLoginActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            etvEmail.apply {
                setOnDeleteButton()
            }

            etvPassword.apply {
                setOnDeleteButton()
            }

            rgLoginType.apply {
                setOnCheckedChangeListener { group, resId ->
                    when (resId) {
                        R.id.rb_gamer_type -> {
                            loginType = CollectionType.GAMER_LIST.type
                            Log.i(TAG, "loginType : $loginType")
                        }
                        R.id.rb_user_type -> {
                            loginType = CollectionType.USER_LIST.type
                            Log.i(TAG, "loginType : $loginType")
                        }
                    }
                }
            }

            btnLogin.setOnClickListener {
                emailLoginViewModel.confirmEmailLogin(
                    etvEmail.text.toString(),
                    etvPassword.text.toString(),
                    loginType
                )
            }

            tvRegister.apply {
                setOnClickListener {
                    startActivity(Intent(this@EmailLoginActivity, RegisterActivity::class.java))
                }
            }




        }
        observing {
            isConfirmed.observe(this@EmailLoginActivity, { isConfirmed ->
                when (isConfirmed) {
                    true -> {

                        val email    = emailLoginViewModel.email.value
                        val name     = emailLoginViewModel.name.value
                        val password = emailLoginViewModel.password.value
                        val uID      = emailLoginViewModel.uID.value
                        val type     = emailLoginViewModel.type.value
                        val tribe    = emailLoginViewModel.tribe.value
                        val gameID   = emailLoginViewModel.gameID.value

                        saveAutoLogin(
                            autoLogin = true,
                            email     = email,
                            name      = name,
                            uID       = uID,
                            password  = password,
                            type      = type,
                            tribe     = tribe,
                            gameID    = gameID)

                        Application.instance?.userUID   = uID
                        Application.instance?.userEmail = email

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