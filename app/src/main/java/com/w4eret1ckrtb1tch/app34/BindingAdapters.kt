package com.w4eret1ckrtb1tch.app34

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @BindingAdapter("srcUrl")
    @JvmStatic
    internal fun ImageView.setImageUrl(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_error)
            .circleCrop()
            .into(this)
    }

//    @BindingAdapter("srcUrl")
//    @JvmStatic
//    fun setImageUrl(imageView: ImageView, url: String) {
//        Glide.with(imageView).load(url).circleCrop().into(imageView)
//    }

}