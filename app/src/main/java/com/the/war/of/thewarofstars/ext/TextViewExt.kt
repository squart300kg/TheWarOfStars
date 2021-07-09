package com.the.war.of.thewarofstars.ext

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("theWarsOfStar:setTitle")
fun TextView.setTitle(inputTitle: String) {
    this.text = inputTitle
}

@BindingAdapter("theWarsOfStar:setPrice")
fun TextView.setPrice(inputPrice: Long) {
    this.text = "$inputPrice / 1판"
}