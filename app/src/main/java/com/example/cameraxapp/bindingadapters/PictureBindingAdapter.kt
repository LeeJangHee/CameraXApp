package com.example.cameraxapp.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cameraxapp.R


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank()) {
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

@BindingAdapter("isButtonView")
fun bindIsButtonView(constraintLayout: ConstraintLayout, visible: Boolean) {
    constraintLayout.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
