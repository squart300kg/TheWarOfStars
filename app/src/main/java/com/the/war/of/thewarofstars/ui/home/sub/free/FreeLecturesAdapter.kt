package com.the.war.of.thewarofstars.ui.home.sub.free

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemFreeLectureBinding
import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import com.the.war.of.thewarofstars.util.GlideUtil

class FreeLecturesAdapter(
    val activity: Activity,
    val watchToVideo: (String, String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<FreeLecturesResponse.Item> = mutableListOf()
    private val TAG = "FreeLecturesAdapterLog"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LecturesViewHolder(
            BR.freeLectureItem,
            parent,
            R.layout.item_free_lecture
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as LecturesViewHolder
        holder.bindItem(items[position])

        holder.itemView.setOnClickListener {
            watchToVideo(items[position].snippet?.resourceId?.videoId.toString(), items[position].snippet?.description.toString())
        }
        holder.initializeYoutube(items[position])



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

    inner class LecturesViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<FreeLecturesResponse.Item, ItemFreeLectureBinding>(itemId, parent, layoutRes) {

        fun initializeYoutube(item: FreeLecturesResponse.Item) {
            Log.i(TAG, "initializeYoutube\n " +
                    "videoId : ${item.snippet?.resourceId?.videoId.toString()}\n " +
                    "videoTitle : ${item.snippet?.title}")

            GlideUtil.loadImage(
                imageView = itemBinding.ivYoutubeThumbnail,
                url       = item.snippet?.thumbnails?.high?.url.toString()
            )
            itemBinding.tvVideoTitle.text = item.snippet?.title
        }



    }



}