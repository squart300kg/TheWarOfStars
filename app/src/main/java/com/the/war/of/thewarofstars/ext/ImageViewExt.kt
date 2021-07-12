package com.the.war.of.thewarofstars.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.the.war.of.thewarofstars.R

@BindingAdapter("theWarsOfStar:setBanner")
fun ImageView.setBanner(imageURL: String) {
    Glide.with(this)
        .load(imageURL)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .error(R.color.black)
        .into(this)
}

@BindingAdapter("theWarsOfStar:setThumbnail")
fun ImageView.setThumbnail(thumbnailURL: String) {
    Glide.with(this)
        .load(thumbnailURL)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .error(R.color.black)
        .into(this)
}