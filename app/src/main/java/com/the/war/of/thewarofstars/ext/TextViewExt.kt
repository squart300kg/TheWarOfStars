package com.the.war.of.thewarofstars.ext

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("theWarsOfStar:setName")
fun TextView.setName(inputTitle: String) {
    this.text = inputTitle
}

@BindingAdapter("theWarsOfStar:setTitle")
fun TextView.setTitle(inputTitle: String) {
    this.text = inputTitle
}

@BindingAdapter("theWarsOfStar:setPrice")
fun TextView.setPrice(inputPrice: Long) {
    val formatter = DecimalFormat("###,###")
    val price = formatter.format(inputPrice)
    this.text = "$price / 1판"
}