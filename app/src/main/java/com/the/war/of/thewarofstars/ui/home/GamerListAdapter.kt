package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemMainGamerListBinding
import com.the.war.of.thewarofstars.model.Gamer

class GamerListAdapter(

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<Gamer> = mutableListOf()

    private val THE_NUBER_OF_HEADER = 1
    private val TAG = "GamerListAdapterLog"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HEADER_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_header, parent, false)
                HeaderViewHolder(view)
            }
            GAMER_TYPE -> {
                GamerListViewHolder(
                    BR.gamerModel,
                    parent,
                    R.layout.item_main_gamer_list)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> HEADER_TYPE
            else -> GAMER_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when( getItemViewType(position)) {
            HEADER_TYPE -> (holder as HeaderViewHolder).bind()
            GAMER_TYPE -> (holder as GamerListViewHolder).bindItem(items[position - THE_NUBER_OF_HEADER])
        }
    }

    override fun getItemCount(): Int {
        return items.size + THE_NUBER_OF_HEADER
    }

    fun loadGamerList(list: List<Gamer>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
        }

    }


    class GamerListViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<Gamer, ItemMainGamerListBinding>(itemId, parent, layoutRes)

    companion object {
        const val HEADER_TYPE = 0
        const val GAMER_TYPE = 1
    }
}