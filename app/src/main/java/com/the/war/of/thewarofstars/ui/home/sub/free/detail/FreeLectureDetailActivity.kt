package com.the.war.of.thewarofstars.ui.home.sub.free.detail

import android.os.Bundle
import android.util.Log
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityFreeLectureDetailBinding

class FreeLectureDetailActivity: BaseActivity<ActivityFreeLectureDetailBinding>(R.layout.activity_free_lecture_detail) {

    private var videoId: String? = null
    private var contents: String? = null

    private val TAG = "FreeLectureDetailActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeValues()

        binding {
            vYoutube.apply {
                play(videoId.toString())
            }
            tvContents.apply {
                text = contents
            }
        }

    }

    private fun initializeValues() {
        videoId = intent.getStringExtra("videoId")
        contents = intent.getStringExtra("contents")
        Log.i(TAG, "videoId : $videoId")
    }
}