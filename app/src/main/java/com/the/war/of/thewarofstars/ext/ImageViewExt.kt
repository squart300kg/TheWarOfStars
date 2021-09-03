package com.the.war.of.thewarofstars.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.util.GlideUtil

@BindingAdapter("theWarsOfStar:setImage")
fun ImageView.setImage(thumbnailURL: String) {

    GlideUtil.loadImage(this, thumbnailURL)

}