package com.techtask.cats.common.imageloader

import android.widget.ImageView

fun ImageView.load(url: String, imageLoader: ImageLoader = GlideImageLoader(context)) {
    imageLoader.loadImage(url, this)
}
