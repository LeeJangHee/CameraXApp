package com.example.cameraxapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.example.cameraxapp.databinding.DialogCustomBinding

enum class DialogType {
    ERROR,
    OK,
    WARNING,
    CANCEL
}

class CustomDialog constructor(context: Context, message:String): Dialog(context) {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    var boolBtnOk: Int = View.VISIBLE
    var boolBtnCancel: Int = View.VISIBLE

    var message: String = message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogMessageAttr.text = message
        binding.dialogCancelAttr.visibility = boolBtnCancel
        binding.dialogOkAttr.visibility = boolBtnOk
    }



    fun oneButtonDialog(dialogType: DialogType) {
        when(dialogType) {
            DialogType.ERROR -> {
                // 취소
                boolBtnOk = View.GONE
            }
            DialogType.CANCEL -> {
                // 취소
                boolBtnOk = View.GONE
            }
            DialogType.WARNING -> {
                // 확인
                boolBtnCancel = View.GONE
            }
            DialogType.OK -> {
                // 확인
                boolBtnCancel = View.GONE
            }
        }
    }

    fun twoButtonDialog() {

    }

}