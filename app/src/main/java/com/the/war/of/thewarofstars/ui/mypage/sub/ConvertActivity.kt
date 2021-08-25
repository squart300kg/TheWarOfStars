package com.the.war.of.thewarofstars.ui.mypage.sub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityConvertBinding

class ConvertActivity: BaseActivity<ActivityConvertBinding>(R.layout.activity_convert) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            ivBack.apply {
                setOnClickListener {
                    onBackPressed()
                }
            }

            rvConvertHistory.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                val convertAdapter      = ConvertAdapter(this@ConvertActivity)

                layoutManager = linearLayoutManager
                adapter       = convertAdapter
            }
        }
    }
}