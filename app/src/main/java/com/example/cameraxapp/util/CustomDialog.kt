package com.example.cameraxapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.cameraxapp.R
import com.example.cameraxapp.databinding.DialogCustomBinding


class CustomDialog constructor(context: Context): Dialog(context) {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    var boolPositiveButton: Int = View.GONE
    var boolNegativeButton: Int = View.GONE
    var boolImage: Int = View.GONE
    var image: Int = R.mipmap.ic_launcher

    var okClickListener: View.OnClickListener? = null
    var cancelClickListener: View.OnClickListener? = null
    var positiveButtonText: String = "확인"
    var negativeButtonText: String = "취소"

    lateinit var message: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.apply {
            dialogMessageAttr.text = message

            dialogCancelAttr.visibility = boolNegativeButton
            dialogCancelAttr.text = negativeButtonText
            dialogCancelAttr.setOnClickListener(cancelClickListener)

            dialogOkAttr.visibility = boolPositiveButton
            dialogOkAttr.text = positiveButtonText
            dialogOkAttr.setOnClickListener(okClickListener)

            dialogImage.visibility = boolImage
            Glide.with(binding.root)
                .load(image)
                .override(200)
                .into(dialogImage)

            setCancelable(false)
        }
    }

    fun setDialogMessage(message: String) {
        this.message = message
    }

    fun setHeaderImage(image: Int) {
        boolImage = View.VISIBLE
        this.image = image
    }

    fun setPositiveButton(positiveButtonText: String, clickListener: View.OnClickListener?) {
        boolPositiveButton = View.VISIBLE
        this.positiveButtonText = positiveButtonText
        okClickListener = clickListener
    }

    fun setNegativeButton(negativeButtonText: String, clickListener: View.OnClickListener?) {
        boolNegativeButton = View.VISIBLE
        this.negativeButtonText = negativeButtonText
        cancelClickListener = clickListener
    }

}