package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager.widget.ViewPager
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val TAG = "HomeFragmentLog"

    private lateinit var bannerAdapter: BannerAdapter

    // 메인 배너 auto scroll
    private val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 5000) {
        var bannerCount: Int? = null
        var currentBannerIndex = 0

        override fun onTick(millisUntilFinished: Long) {

            binding {
                bannerRecyclerView.apply {
                    bannerCount = bannerAdapter.itemCount
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttack")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        homeViewModel.getGamers()
        homeViewModel.getBanners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")

        binding {

            homeVm = homeViewModel

            bannerRecyclerView.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                bannerAdapter = BannerAdapter()
                layoutManager = linearLayoutManager
                adapter = bannerAdapter

                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)





            }



            gamerListRecyclerView.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                layoutManager = linearLayoutManager
                addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
//                        outRect.right = dpToPx(6)
//                        outRect.left = dpToPx(6)
                        outRect.bottom = dpToPx(10)
                        outRect.top = dpToPx(10)

                    }
                })
                adapter = GamerListAdapter()

            }
        }

        observing {
            bannerList.observe(requireActivity(), {
                countDownTimer.start()


            })
        }

    }

    override fun onResume() {
        super.onResume()
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