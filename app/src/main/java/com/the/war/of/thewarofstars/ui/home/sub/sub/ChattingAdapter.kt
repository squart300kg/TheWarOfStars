package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemChattingLeftBinding
import com.the.war.of.thewarofstars.databinding.ItemChattingRightBinding
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

        return if (items[position].uid == receiver) {
            LEFT
        } else {
            RIGHT
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == LEFT) {
            ChattingLeftViewHolder(
                BR.chattingModel,
                parent,
                R.layout.item_chatting_left
            )
        } else {
            ChattingRightViewHolder(
                BR.chattingModel,
                parent,
                R.layout.item_chatting_right
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            LEFT  -> (holder as ChattingLeftViewHolder).bindItem(items[position])
            RIGHT -> (holder as ChattingRightViewHolder).bindItem(items[position])
        }

    }

    override fun getItemCount(): Int = items.size

    class ChattingLeftViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ChattingItem, ItemChattingLeftBinding>(itemId, parent, layoutRes) {

        val TAG = "ChattingViewHolderLog"

    }

    class ChattingRightViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ChattingItem, ItemChattingRightBinding>(itemId, parent, layoutRes) {

        val TAG = "ChattingViewHolderLog"

    }


    fun loadOneBalloon(chattingItem: ChattingItem) {
        items.add(chattingItem)
        notifyItemInserted(items.size - 1)

    }

    fun loadAllBallon(chattingHistory: List<ChattingItem>) {
        Log.i(TAG, "list : $chattingHistory")
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
        const val LEFT  = 0
        const val RIGHT = 1

    }
}


