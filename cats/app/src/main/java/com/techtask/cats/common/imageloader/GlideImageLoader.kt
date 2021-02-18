package com.techtask.cats.common.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader(private val context: Context) : ImageLoader {

    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }
}
