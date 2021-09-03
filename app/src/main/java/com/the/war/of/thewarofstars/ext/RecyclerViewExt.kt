package com.the.war.of.thewarofstars.ext


import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.model.BannerItem
import com.the.war.of.thewarofstars.model.GamerItem
import com.the.war.of.thewarofstars.model.ReviewItem
import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import com.the.war.of.thewarofstars.ui.home.BannerAdapter
import com.the.war.of.thewarofstars.ui.home.GamerListAdapter
import com.the.war.of.thewarofstars.ui.home.sub.FreeLecturesAdapter
import com.the.war.of.thewarofstars.ui.home.sub.ReviewAdapter

@BindingAdapter("theWarsOfStar:setItems")
fun <T> RecyclerView.setItems(items: List<T>?) {
    val TAG = "BindingAdapterLog_RecyclerView"
    Log.i(TAG, items.toString())
    items?.let {
        when(adapter) {
            is GamerListAdapter -> {
                (adapter as GamerListAdapter).loadGamerList(items as List<GamerItem>)
            }
            is BannerAdapter -> {
                (adapter as BannerAdapter).loadBannerList(items as List<BannerItem>)
            }
            is ReviewAdapter -> {
                (adapter as ReviewAdapter).loadReviewList(items as List<ReviewItem>)
            }
            is FreeLecturesAdapter -> {
                (adapter as FreeLecturesAdapter).loadLectureList(items as List<FreeLecturesResponse.Item>)
            }
//            is ChattingAdapter -> {
//                (adapter as ChattingAdapter).loadAllBallon(items as List<ChattingItem>)
//            }

        }
    }
}