package com.example.cameraxapp.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cameraxapp.R


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .into(view)
    } else {
        Glide.with(view.context)
            .load(R.mipmap.ic_launcher)
            .centerCrop()
            .into(view)
    }
}
