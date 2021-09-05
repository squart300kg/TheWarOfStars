package com.the.war.of.thewarofstars.ui.home.sub.free

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.SkeletonScreen
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityFreeLectureBinding
import com.the.war.of.thewarofstars.ui.home.sub.free.detail.FreeLectureDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FreeLectureActivity: BaseActivity<ActivityFreeLectureBinding>(R.layout.activity_free_lecture) {

    private val freeLectureViewModel: FreeLectureViewModel by viewModel()

    private lateinit var freeLecturesAdapter: FreeLecturesAdapter
    private var skeletonScreen: SkeletonScreen? = null

    private val TAG = "FreeLectureActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {

            freeVm = freeLectureViewModel

            ivBack.apply {
                setOnClickListener { onBackPressed() }
            }

            rvLectures.apply {
                setHasFixedSize(true)
                val linearlayoutManager = LinearLayoutManager(this@FreeLectureActivity, RecyclerView.VERTICAL, false)
                val freeLecturesAdapter = FreeLecturesAdapter(this@FreeLectureActivity) {
                    videoId, contents ->
                    Log.i(TAG, "videoId : $videoId")
                    Intent(this@FreeLectureActivity, FreeLectureDetailActivity::class.java).apply {
                        putExtra("contents", contents)
                        putExtra("videoId", videoId)
                        startActivity(this)
                    }
                }

                layoutManager = linearlayoutManager
                adapter = freeLecturesAdapter
//                skeletonScreen = Skeleton.bind(this)
//                    .adapter(freeLecturesAdapter)
//                    .shimmer(true)
//                    .angle(20)
//                    .frozen(false)
//                    .duration(1200)
//                    .count(10)
//                    .color(R.color.shimmer_color)
//                    .load(R.layout.skeleton_banner)
//                    .show()
            }


        }



    }

    override fun onStart() {
        super.onStart()

        if (freeLectureViewModel.freeLectureList.value == null) {
            freeLectureViewModel.getAllFreeLecture(
                playListId = "PLgqfGVjqF_-aGO7LV8_peXquXd0TsX2aX",
                apiKey     = getString(R.string.youtube_api_key)
            )
        }

    }
}