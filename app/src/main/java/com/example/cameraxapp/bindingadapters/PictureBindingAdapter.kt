package com.example.cameraxapp.bindingadapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cameraxapp.R


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: View, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank()) {
        when(view) {
            is ImageView -> {
                if (view.id == R.id.album_no_imageView) {
                    view.visibility = View.INVISIBLE
                } else if (view.id == R.id.iv_picture) {
                    Glide.with(view.context)
                        .load(imageUrl)
                        .centerCrop()
                        .into(view)
                } else {
                    view.visibility = View.VISIBLE
                    Glide.with(view.context)
                        .load(imageUrl)
                        .centerCrop()
                        .into(view)
                }

            }
            is TextView -> {
                view.visibility = View.VISIBLE
            }
        }
    } else {
        when(view) {
            is ImageView -> {
                if (view.id == R.id.album_no_imageView) {
                    view.visibility = View.VISIBLE
                } else if (view.id == R.id.iv_picture) {
                    Glide.with(view.context)
                        .load(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(view)
                } else {
                    view.visibility = View.INVISIBLE
                }
            }
            is TextView -> {
                view.visibility = View.INVISIBLE
            }
        }
    }
}

@BindingAdapter("isButtonView")
fun bindIsButtonView(linearLayout: LinearLayout, visible: Boolean) {
    linearLayout.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
