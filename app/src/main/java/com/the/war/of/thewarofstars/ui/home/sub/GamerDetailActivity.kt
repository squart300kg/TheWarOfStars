package com.the.war.of.thewarofstars.ui.home.sub

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityGamerDetailBinding
import com.the.war.of.thewarofstars.ext.setThumbnail
import com.the.war.of.thewarofstars.util.DataInput
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class GamerDetailActivity: BaseActivity<ActivityGamerDetailBinding>(R.layout.activity_gamer_detail) {

    private val gamerDetailViewModel: GamerDetailViewModel by viewModel()

    private var reviewListSkeletonScreen: SkeletonScreen? = null
    private var gamerDetailSkeletonScreen: SkeletonScreen? = null

    private var name: String?         = null
    private var price: Long?          = null
    private var thumbnailURL: String? = null
    private var title: String?        = null
    private var description: String?  = null

    val TAG = "GamerDetailActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name         = intent.getStringExtra("name")
        price        = intent.getLongExtra("price", 0)
        thumbnailURL = intent.getStringExtra("thumbnailURL")
        title        = intent.getStringExtra("title")
        description  = intent.getStringExtra("description")

        loadGamerInfo(name, price, thumbnailURL, title, description)


        binding {

            gamerDetailModel = gamerDetailViewModel

            tvGamerTitle.text = title

            ivBack.setOnClickListener { onBackPressed() }

            ivGamerThumbnail.setOnClickListener {
//                DataInput.reviewListInsert()
            }

            layoutGamerDetail.apply {
                gamerDetailSkeletonScreen = Skeleton.bind(this)
                    .shimmer(true)
                    .angle(20)
                    .duration(1200)
                    .color(R.color.shimmer_color)
                    .load(R.layout.skeleton_gamer_detail)
                    .show()
            }

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

                reviewListSkeletonScreen = Skeleton.bind(this)
                    .adapter(reviewAdapter)
                    .shimmer(true)
                    .angle(20)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .color(R.color.shimmer_color)
                    .load(R.layout.skeleton_review_list)
                    .show()
            }

        }

        observing {
            reviewList.observe(this@GamerDetailActivity, {
                reviewListSkeletonScreen?.hide()
                gamerDetailSkeletonScreen?.hide()
            })

        }


    }

    private fun loadGamerInfo(name: String?, price: Long?, thumbnailURL: String?, title: String?, description: String?) {
        binding {

            Log.i(TAG, "name : $name, price : $price, thumbailURL : $thumbnailURL, title : $title, description : $description")

            Glide.with(ivGamerThumbnail)
                .load(thumbnailURL)
                .transform(
                    CenterCrop(),
                    RoundedCorners(20))
                .placeholder(R.color.black)
                .error(R.color.black)
                .into(ivGamerThumbnail)
            tvGamerTitle.text = title
            tvGamerPrice.text = DecimalFormat("###,###").format(price) + "원 / 1판"
            tvGamerDescription.text = description?.replace("\\n", "\n")
            tvDescription.text = "${name}선수 상세보기"
        }

    }

    override fun onResume() {
        super.onResume()

        if (gamerDetailViewModel.reviewList.value == null) {
            gamerDetailViewModel.getReviews(name)
        }

    }

    private fun observing(action: GamerDetailViewModel.() -> Unit) {
        gamerDetailViewModel.run(action)

    }
}