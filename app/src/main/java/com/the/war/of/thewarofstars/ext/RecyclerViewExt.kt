package com.the.war.of.thewarofstars.ext


import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.model.Gamer
import com.the.war.of.thewarofstars.ui.home.GamerListAdapter

@BindingAdapter("theWarsOfStar:setItems")
fun <T> RecyclerView.setItems(items: List<T>?) {
    val TAG = "BindingAdapterLog_RecyclerView"
    Log.i(TAG, items.toString())
    items?.let {
        when(adapter) {
            is GamerListAdapter -> {
                (adapter as GamerListAdapter).loadGamerList(items as List<Gamer>)
            }
        }
    }
}