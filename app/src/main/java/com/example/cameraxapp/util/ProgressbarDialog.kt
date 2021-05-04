package com.example.cameraxapp.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.cameraxapp.R
import kotlinx.android.synthetic.main.dialog_progressbar.*

class ProgressbarDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progressbar)

        Thread() {
            for (i in 0..100) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressbar.progress = i
            }
        }.start()
    }
}