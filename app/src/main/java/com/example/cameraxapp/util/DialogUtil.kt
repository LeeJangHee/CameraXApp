package com.example.cameraxapp.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class DialogUtil {

    fun showDialogPermissionCheck(activity: Activity, deniedPermissionList: ArrayList<String>) {
        // 알람 확인
        var deniedString = ""
        val listIndex = deniedPermissionList.size - 1
        for (i in 0 until listIndex) {
            deniedString += "${deniedPermissionList[i]}, "
        }
        deniedString += "${deniedPermissionList[listIndex]} 권한이 없습니다."

        val dialog = AlertDialog.Builder(activity)
            .setMessage(deniedString)
            .setPositiveButton("확인") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + activity.packageName)
                activity.startActivity(intent)
            }
            .create()

        dialog.show()
    }
}