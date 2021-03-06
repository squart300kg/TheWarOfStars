package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentHomeBinding
import com.the.war.of.thewarofstars.util.CirclePagerIndicatorDecoration

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val TAG = "HomeFragmentLog"

    private lateinit var bannerListAdapter: BannerAdapter
    private lateinit var gamerListAdapter: GamerListAdapter

    private var bannerSkeletonScreen: SkeletonScreen? = null
    private var gamerListSkeletonScreen: SkeletonScreen? = null
    private var tribeTypeSkeletonScreen: SkeletonScreen? = null

    // 메인 배너 auto scroll
    private val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 5000) {
        var bannerCount: Int? = null
        var currentBannerIndex = 0

        override fun onTick(millisUntilFinished: Long) {

            binding {
                bannerRecyclerView.apply {
                    bannerCount = bannerListAdapter.itemCount
                    smoothScrollToPosition(currentBannerIndex)
                    currentBannerIndex++

                    if (currentBannerIndex == bannerCount)
                        currentBannerIndex = 0
                }
            }
        }
        override fun onFinish() {
            start()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")

        binding {

            homeVm = homeViewModel

            layoutCategory.apply {
                tribeTypeSkeletonScreen = Skeleton.bind(this)
                    .shimmer(true)
                    .angle(20)
                    .duration(1200)
                    .color(R.color.shimmer_color)
                    .load(R.layout.skeleton_main_type)
                    .show()
            }

            bannerRecyclerView.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                bannerListAdapter = BannerAdapter(requireActivity())
                layoutManager = linearLayoutManager
                adapter = bannerListAdapter

                bannerSkeletonScreen = Skeleton.bind(bannerRecyclerView)
                    .adapter(bannerListAdapter)
                    .shimmer(true)
                    .angle(20)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .color(R.color.shimmer_color)
                    .load(R.layout.skeleton_banner)
                    .show()

                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)

                addItemDecoration(CirclePagerIndicatorDecoration())
            }

            gamerListRecyclerView.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                gamerListAdapter = GamerListAdapter(requireActivity())
                layoutManager = linearLayoutManager
                adapter = GamerListAdapter(requireActivity())

                gamerListSkeletonScreen = Skeleton.bind(gamerListRecyclerView)
                    .adapter(gamerListAdapter)
                    .shimmer(true)
                    .angle(20)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .color(R.color.shimmer_color)
                    .load(R.layout.skeleton_gamer_list)
                    .show()

                addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.bottom = dpToPx(10)
                        outRect.top = dpToPx(10)

                    }
                })

            }
        }

        observing {
            bannerItemList.observe(requireActivity(), {
                countDownTimer.start()
            })

            gamerItemList.observe(requireActivity(), {
                gamerListSkeletonScreen?.hide()
                bannerSkeletonScreen?.hide()
                tribeTypeSkeletonScreen?.hide()
            })
        }

    }

    override fun onResume() {
        super.onResume()

        if (homeViewModel.bannerItemList.value == null) {
            homeViewModel.getBanners()
        }

        if (homeViewModel.gamerItemList.value == null) {
            homeViewModel.getGamers()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach")
    }

    private fun observing(action: HomeViewModel.() -> Unit) {
        homeViewModel.run(action)
    }

}