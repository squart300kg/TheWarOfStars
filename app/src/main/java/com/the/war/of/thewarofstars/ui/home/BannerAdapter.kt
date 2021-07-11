package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.model.Banner

class BannerAdapter(private val onClick: (Int) -> Unit): PagerAdapter()  {

    private val items: MutableList<Banner> = mutableListOf()
    private val TAG = "BannerAdapterLog"
    override fun getCount(): Int = Int.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_auto_viewpager, container, false)


//        val mainContent = mainRecommendContents[position.rem(mainRecommendContents.size)]
//
        Glide.with(container.context)
            .load(items[position].imageURL)
            .apply(RequestOptions().centerCrop())
            .into(view.)

        view.setOnClickListener {
            onClick(position)
        }

        container.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun loadBannerList(list: List<Banner>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }
}