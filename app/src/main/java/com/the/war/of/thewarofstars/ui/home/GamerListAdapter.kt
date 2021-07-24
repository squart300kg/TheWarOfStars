package com.the.war.of.thewarofstars.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemMainGamerListBinding
import com.the.war.of.thewarofstars.model.GamerItem
import com.the.war.of.thewarofstars.ui.home.sub.GamerDetailActivity

class GamerListAdapter(
    val context: Context
): RecyclerView.Adapter<GamerListAdapter.GamerListViewHolder>() {

    private val items: MutableList<GamerItem> = mutableListOf()

    private val TAG = "GamerListAdapterLog"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamerListViewHolder {
        return GamerListViewHolder(
            BR.gamerModel,
            parent,
            R.layout.item_main_gamer_list)
    }


    override fun onBindViewHolder(holder: GamerListViewHolder, position: Int) {
        val viewHolder = holder
        viewHolder.bindItem(items[position])
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, GamerDetailActivity::class.java)
            intent.putExtra("name", items[position].name)
            intent.putExtra("price", items[position].price)
            intent.putExtra("thumbnailURL", items[position].thumbnailURL)
            intent.putExtra("title", items[position].title)
            intent.putExtra("description", items[position].description)

            Log.i(TAG, "price : ${items[position].price}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun loadGamerList(list: List<GamerItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }

    class GamerListViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<GamerItem, ItemMainGamerListBinding>(itemId, parent, layoutRes)

}