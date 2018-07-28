package com.pasotti.matteo.wikiheroes.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url : String) {
    Glide.with(imageView.context).load(url).into(imageView)
}