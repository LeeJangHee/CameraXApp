package com.example.cameraxapp.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.cameraxapp.R

class ProgressDialog constructor(context: Context) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
    }
}