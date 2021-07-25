package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemBannerBinding
import com.the.war.of.thewarofstars.databinding.ItemChattingBinding
import com.the.war.of.thewarofstars.model.BannerItem
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.ui.home.BannerAdapter

/**
 * Created by sangyoon on 2021/07/25
 */
class ChattingAdapter(
    val activity: Activity
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val items: MutableList<ChattingItem> = mutableListOf()
    private val TAG = "ChattingAdapterLog"

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChattingViewHolder(
            BR.chattingModel,
            parent,
            R.layout.item_chatting
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = holder as ChattingViewHolder
        viewHolder.bindItem(items[position])

    }

    override fun getItemCount(): Int = items.size

    class ChattingViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ChattingItem, ItemChattingBinding>(itemId, parent, layoutRes)

    fun addOneBalloon(chattingItem: ChattingItem) {
        items.add(chattingItem)
        notifyItemInserted(items.size - 1)

    }

    companion object {

    }
}