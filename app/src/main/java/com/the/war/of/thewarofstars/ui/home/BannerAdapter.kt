package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemBannerBinding
import com.the.war.of.thewarofstars.databinding.ItemMainGamerListBinding
import com.the.war.of.thewarofstars.model.Banner
import com.the.war.of.thewarofstars.model.Gamer

class BannerAdapter(

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<Banner> = mutableListOf()
    private val TAG = "BannerAdapterLog"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(
            BR.bannerModel,
            parent,
            R.layout.item_banner
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val holder = viewHolder as BannerViewHolder
        holder.bindItem(items[position])


    }

    override fun getItemCount(): Int = items.size

    fun loadBannerList(list: List<Banner>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }

    class BannerViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<Banner, ItemBannerBinding>(itemId, parent, layoutRes) {

    }



}