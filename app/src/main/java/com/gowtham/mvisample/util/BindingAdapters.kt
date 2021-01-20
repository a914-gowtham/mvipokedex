package com.gowtham.mvisample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gowtham.mvisample.R

object BindingAdapters {

    @BindingAdapter("imageUrl","placeHolder")
    @JvmStatic
    fun loadImage(view: ImageView, url: String,resId: Int=R.drawable.ic_launcher_background) {
        if (url.isEmpty())
            view.setImageResource(resId)
        else {
            Glide.with(view.context).load(url)
                .apply(returnCache(resId))
                .into(view)
        }
    }

    private fun returnCache(pHolder: Int): RequestOptions {
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(pHolder).error(pHolder)
    }

}