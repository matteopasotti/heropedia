package com.pasotti.matteo.wikiheroes.utils

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.RequestOptions.centerCropTransform

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url : String?) {
    val requestOptions = RequestOptions()
    requestOptions.centerCrop()

    if(url != null) {
        Glide.with(imageView.context)
                .load(url)
                .apply(requestOptions)
                .into(imageView)
    }

}

@BindingAdapter("galleryImageUrl")
fun setGalleryImageUrl(imageView: ImageView, url : String) {

    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }

}

@BindingAdapter("circularImageUrl")
fun setCircularImageUrl(imageView: ImageView, url : String) {
    val requestOptions = RequestOptions()
    requestOptions.circleCrop()


    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}


@BindingAdapter("blurImageUrl")
fun setBlurImageUrl(imageView: ImageView, url : String) {

    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().transform(BlurTransformation(25 , 2)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}