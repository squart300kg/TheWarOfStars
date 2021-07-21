package com.the.war.of.thewarofstars.ui.home.sub

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemReviewBinding
import com.the.war.of.thewarofstars.model.ReViewItem

class ReviewAdapter(
    val context: Context
): RecyclerView.Adapter<ReviewAdapter.ReviewListViewHolder>()  {

    private val items: MutableList<ReViewItem> = mutableListOf()

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
//        holder.bindItem(items[position])
    }

//    override fun getItemCount(): Int = items.size
    override fun getItemCount(): Int = 20

    class ReviewListViewHolder(
        val context: Context,
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ReViewItem, ItemReviewBinding>(itemId, parent, layoutRes)

}