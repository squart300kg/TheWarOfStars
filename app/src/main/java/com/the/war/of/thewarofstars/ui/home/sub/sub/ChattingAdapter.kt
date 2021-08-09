package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemChattingBinding
import com.the.war.of.thewarofstars.model.ChattingItem

/**
 * Created by sangyoon on 2021/07/25
 */
class ChattingAdapter(
    val activity: Activity
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val items: MutableList<ChattingItem> = mutableListOf()

    private var receiver: String? = null
    private var sender  : String? = null

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
        viewHolder.initializeBalloonHost(items[position].to.toString(), receiver.toString())

    }

    override fun getItemCount(): Int = items.size

    class ChattingViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ChattingItem, ItemChattingBinding>(itemId, parent, layoutRes) {

        val TAG = "ChattingViewHolderLog"

        fun initializeBalloonHost(to: String, receiver: String) {
            /**
             * 말풍선을 왼쪽으로 정렬
             */
            if (to == receiver) {
                itemBinding.layoutBalloon.gravity = Gravity.LEFT
                itemBinding.tvLeftDate.visibility = View.GONE
                itemBinding.tvRightDate.visibility = View.VISIBLE
            }

            /**
             * 말풍선을 오른쪽으로 정렬
             */
            else {
                itemBinding.layoutBalloon.gravity = Gravity.RIGHT
                itemBinding.tvLeftDate.visibility = View.VISIBLE
                itemBinding.tvRightDate.visibility = View.GONE
            }

        }
    }


    fun loadOneBalloon(chattingItem: ChattingItem) {
        items.add(chattingItem)
        notifyItemInserted(items.size - 1)

    }

    fun loadAllBallon(chattingHistory: List<ChattingItem>) {
        items.clear()
        items.addAll(chattingHistory)
        notifyDataSetChanged()
    }

    fun setReceiver(value: String) {
        receiver = value
    }

    fun setSender(value: String) {
        sender = value
    }


    companion object {

    }
}


