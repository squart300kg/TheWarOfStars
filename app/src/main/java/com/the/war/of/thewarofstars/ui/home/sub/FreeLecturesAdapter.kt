package com.the.war.of.thewarofstars.ui.home.sub

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemBannerBinding
import com.the.war.of.thewarofstars.model.BannerItem
import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse

class FreeLecturesAdapter(
    val activity: Activity
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<FreeLecturesResponse.Item> = mutableListOf()
    private val TAG = "BannerAdapterLog"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LecturesViewHolder(
            BR.bannerModel,
            parent,
            R.layout.item_banner
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as LecturesViewHolder
        holder.bindItem(items[position])

    }

    override fun getItemCount(): Int = items.size

    fun loadLectureList(list: List<FreeLecturesResponse.Item>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }

    class LecturesViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<FreeLecturesResponse.Item, ItemBannerBinding>(itemId, parent, layoutRes) {

    }



}