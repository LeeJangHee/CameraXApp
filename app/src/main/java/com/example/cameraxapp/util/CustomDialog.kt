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

    var boolBtnOk: Int = View.VISIBLE
    var boolBtnCancel: Int = View.VISIBLE
    var boolImage: Int = View.GONE
    var image: String = R.mipmap.ic_launcher.toString()

    var okClickListener: View.OnClickListener? = null
    var cancelClickListener: View.OnClickListener? = null
    var btnOkText: String = "확인"
    var btnCancelText: String = "취소"

    lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.apply {
            dialogMessageAttr.text = message

            dialogCancelAttr.visibility = boolBtnCancel
            dialogCancelAttr.text = btnCancelText
            dialogCancelAttr.setOnClickListener(cancelClickListener)

            dialogOkAttr.visibility = boolBtnOk
            dialogOkAttr.text = btnOkText
            dialogOkAttr.setOnClickListener(okClickListener)

            dialogImage.visibility = boolImage
            Glide.with(binding.root)
                .load(image)
                .into(dialogImage)
        }
    }

    fun setButtonText(btnOkText: String, btnCancelText: String) {
        this.btnOkText = btnOkText
        this.btnCancelText = btnCancelText
    }

    fun setContent(message: String) {
        this.message = message
    }

    fun setHeaderImage(image: String) {
        boolImage = View.VISIBLE
        this.image = image
    }

    fun okButtonDialog(clickListener: View.OnClickListener) {
        boolBtnOk = View.GONE
        okClickListener = clickListener
    }

    fun cancelButtonDialog(clickListener: View.OnClickListener) {
        boolBtnCancel = View.GONE
        cancelClickListener = clickListener
    }

    fun twoButtonDialog(okClickListener: View.OnClickListener, cancelClickListener: View.OnClickListener) {
        this.okClickListener = okClickListener
        this.cancelClickListener = cancelClickListener
    }

}