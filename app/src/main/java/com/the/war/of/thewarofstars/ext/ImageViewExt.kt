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

@BindingAdapter("theWarsOfStar:setBanner")
fun ImageView.setBanner(imageURL: String) {
    Glide.with(this)
        .load(imageURL)
        .apply(
            RequestOptions.bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(dpToPx(6))
                )
            )
        )
        .placeholder(R.color.black)
        .error(R.color.black)
        .into(this)
}

@BindingAdapter("theWarsOfStar:setThumbnail")
fun ImageView.setThumbnail(thumbnailURL: String) {
    Glide.with(this)
        .load(thumbnailURL)
        .apply(
            RequestOptions.bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(dpToPx(6))
                )
            )
        )
        .placeholder(R.color.black)
        .error(R.color.black)
        .into(this)
}