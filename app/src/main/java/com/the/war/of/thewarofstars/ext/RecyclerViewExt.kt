package com.the.war.of.thewarofstars.ext


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("theWarsOfStar:setItems")
fun <T> RecyclerView.setItems(items: List<T>?) {
    items?.let {

    }
}