package com.the.war.of.thewarofstars.ui.home.sub

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemReviewBinding
import com.the.war.of.thewarofstars.model.ReviewItem

class ReviewAdapter(
    val context: Context
): RecyclerView.Adapter<ReviewAdapter.ReviewListViewHolder>()  {

    private val items: MutableList<ReviewItem> = mutableListOf()

    val TAG = "ReviewAdapterLog"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewListViewHolder {
        return ReviewListViewHolder(
            context,
            BR.reviewModel,
            parent,
            R.layout.item_review
        )
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ReviewListViewHolder(
        val context: Context,
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ReviewItem, ItemReviewBinding>(itemId, parent, layoutRes)

    fun loadReviewList(list: List<ReviewItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }
}