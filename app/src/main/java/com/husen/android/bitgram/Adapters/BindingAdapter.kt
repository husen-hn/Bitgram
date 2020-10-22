package com.husen.android.bitgram.Adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imgUrl: String) {
    view.load(imgUrl)
}

@BindingAdapter("app:setColor")
fun setPercentColor(view: TextView, bitPercentColor: Int) {
    view.setTextColor(ContextCompat.getColor(view.context, bitPercentColor))
}