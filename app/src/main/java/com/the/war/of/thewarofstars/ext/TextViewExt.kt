package com.the.war.of.thewarofstars.ext

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@BindingAdapter("theWarsOfStar:setText")
fun TextView.setText(text: String) {
    val TAG = "BindingAdapter_setTextLog"
    Log.i(TAG, text)
    this.text = text?.replace("\\n", "\n")
}

@BindingAdapter("theWarsOfStar:setReviewCount")
fun TextView.setReviewCount(text: Long) {
    val TAG = "BindingAdapter_setReviewCountLog"
    Log.i(TAG, text.toString())
    this.text = "후기 ${text}개"
}

@BindingAdapter("theWarsOfStar:setDate")
fun TextView.setDate(timestamp: Timestamp) {
    val TAG = "BindingAdapter_setDateLog"
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    val result = simpleDateFormat.format(timestamp.toDate())
    Log.i(TAG, timestamp.toString())
    Log.i(TAG, timestamp.toDate().toString())
    Log.i(TAG, result)
    this.text = result
}


@BindingAdapter("theWarsOfStar:setPrice")
fun TextView.setPrice(inputPrice: Long) {
    val formatter = DecimalFormat("###,###")
    val price = formatter.format(inputPrice)
    this.text = "$price / 1판"
}