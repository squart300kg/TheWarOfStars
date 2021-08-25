package com.the.war.of.thewarofstars.ui.home.sub.pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityPayCompleteOkBinding

class PayCompleteOkActivity: BaseActivity<ActivityPayCompleteOkBinding>(R.layout.activity_pay_complete_ok) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            layoutOk.apply {
                setOnClickListener {
                    goMain()
                }
            }
        }


    }

    private fun goMain() {
        val intent = Intent(this@PayCompleteOkActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}