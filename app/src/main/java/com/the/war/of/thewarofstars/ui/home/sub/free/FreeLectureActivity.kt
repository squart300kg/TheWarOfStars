package com.the.war.of.thewarofstars.ui.home.sub.free

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityFreeLectureBinding
import com.the.war.of.thewarofstars.ui.home.FreeLectureViewModel
import com.the.war.of.thewarofstars.ui.home.sub.FreeLecturesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FreeLectureActivity: BaseActivity<ActivityFreeLectureBinding>(R.layout.activity_free_lecture) {

    private val freeLectureViewModel: FreeLectureViewModel by viewModel()

    private lateinit var freeLecturesAdapter: FreeLecturesAdapter
    private var skeletonScreen: SkeletonScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {

            freeVm = freeLectureViewModel

            tvTitle.apply {
                setOnClickListener {

                }
            }

            rvLectures.apply {
                setHasFixedSize(true)
                val linearlayoutManager = LinearLayoutManager(this@FreeLectureActivity, RecyclerView.VERTICAL, false)
                val freeLecturesAdapter = FreeLecturesAdapter(this@FreeLectureActivity)

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
                playListId = "PLYtRmejV1_MxwLBNLYaiKdQnqTzxu_PBm",
                apiKey     = getString(R.string.youtube_api_key)
            )
        }

    }
}