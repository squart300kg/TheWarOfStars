package com.the.war.of.thewarofstars.ext

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.the.war.of.thewarofstars.model.Banner
import com.the.war.of.thewarofstars.ui.home.BannerAdapter

@BindingAdapter("theWarsOfStar:setItems")
fun ViewPager.setItems(items: List<Banner>?) {
    val TAG = "BindingAdapterLog_ViewPager"
    Log.i(TAG, items.toString())

    items?.let {
        (adapter as BannerAdapter).loadBannerList(items)
    }
}