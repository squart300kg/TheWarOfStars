package com.the.war.of.thewarofstars.ui.home

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R

class GamerListAdapter(

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HEADER_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_header, parent, false)
                HeaderViewHolder(view)
            }
            GAMER_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_gamer_list, parent, false)
                GamerListViewHolder(view)
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
            GAMER_TYPE -> (holder as GamerListViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = 10

    inner class GamerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
        }

    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

//            itemView.imageViewTerran.background = ShapeDrawable(OvalShape())
//            itemView.imageViewZerg.background = ShapeDrawable(OvalShape())
//            itemView.imageViewProtoss.background = ShapeDrawable(OvalShape())
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                itemView.imageViewTerran.clipToOutline = true
//                itemView.imageViewZerg.clipToOutline = true
//                itemView.imageViewProtoss.clipToOutline = true
//            }
        }

    }
    companion object {
        const val HEADER_TYPE = 0
        const val GAMER_TYPE = 1
    }
}