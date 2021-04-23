package com.example.cameraxapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.cameraxapp.R
import kotlinx.android.synthetic.main.dialog_custom.*


class CustomDialog constructor(context: Context, message:String): Dialog(context) {

    var message: String = message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog_message.text = message


    }
}