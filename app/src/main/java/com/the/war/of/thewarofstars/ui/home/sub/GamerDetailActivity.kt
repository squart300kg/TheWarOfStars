package com.the.war.of.thewarofstars.ui.home.sub

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityGamerDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamerDetailActivity: BaseActivity<ActivityGamerDetailBinding>(R.layout.activity_gamer_detail) {

    private val gamerDetailViewModel: GamerDetailViewModel by viewModel()

    private var reviewListSkeletonScreen: SkeletonScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {

//            gamerDetailModel = gamerDetailViewModel

            ivBack.setOnClickListener { onBackPressed() }
            rvReview.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(this@GamerDetailActivity, RecyclerView.VERTICAL, false)
                val reviewAdapter = ReviewAdapter(this@GamerDetailActivity)

                layoutManager = linearLayoutManager
                adapter = reviewAdapter

                addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.bottom = DensityUtils.dpToPx(20)

                    }
                })

//                reviewListSkeletonScreen = Skeleton.bind(this)
//                    .adapter(reviewAdapter)
//                    .shimmer(true)
//                    .angle(20)
//                    .frozen(false)
//                    .duration(1200)
//                    .count(10)
//                    .color(R.color.shimmer_color)
//                    .load(R.layout.item_review)
//                    .show()
            }

        }

        observing {


        }


    }

    override fun onResume() {
        super.onResume()

    }

    private fun observing(action: GamerDetailViewModel.() -> Unit) {
        gamerDetailViewModel.run(action)

    }
}