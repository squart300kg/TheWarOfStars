package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.the.war.of.thewarofstars.R

class BannerAdapter(private val onClick: (Int) -> Unit): PagerAdapter()  {
    override fun getCount(): Int = Int.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_auto_viewpager, container, false)

//        val mainContent = mainRecommendContents[position.rem(mainRecommendContents.size)]
//
//        Glide.with(container.context)
//            .load(mainContent.imageUrl)
//            .apply(RequestOptions().centerCrop())
//            .into(view.coverImageView)

        view.setOnClickListener {
            onClick(position)
        }

        container.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}