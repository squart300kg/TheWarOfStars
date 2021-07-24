package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityQuestionBinding

class QuestionActivity: BaseActivity<ActivityQuestionBinding>(R.layout.activity_question) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            tvChattingDescription.apply {
                text = intent.getStringExtra("name") + "선수님께 보내는 메시지"
            }
        }


    }
}