package com.the.war.of.thewarofstars.ui.mypage.sub

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityConvertInputBinding

class ConvertInputActivity: BaseActivity<ActivityConvertInputBinding>(R.layout.activity_convert_input) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            etInput.apply {
                setOnDeleteButton()
            }

            tvNumberOne.apply {
                setOnClickListener {
                    etInput.text = etInput.text.toString() + "1"
                }
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun TextView.setOnDeleteButton() {
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

                checkConvertButtonEnable()
            }
        })
    }

    private fun checkConvertButtonEnable() {
        // 1. 금액이 입력되지 않았을시 비활성화
        // 2. 금액이 한도액을 넘었을시 비활성화
//        val email = dataBinding.etvEmail.text.toString()
//        val password = dataBinding.etvPassword.text.toString()
//
//        dataBinding.btnLogin.isSelected = email.isNotEmpty()
//                && password.isNotEmpty()
//                && password.length >= 8
    }
}