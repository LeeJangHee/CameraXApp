package com.example.cameraxapp.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.example.cameraxapp.R

class DialogUtil {

    fun showDialogPermissionCheck(activity: Activity, deniedPermissionList: ArrayList<String>) {
        // 알람 확인
        var deniedString = ""
        val listIndex = deniedPermissionList.size - 1
        for (i in 0 until listIndex) {
            deniedString += "${deniedPermissionList[i]}, "
        }
        deniedString += "${deniedPermissionList[listIndex]} 권한이 없습니다."


        CustomDialog(activity)
            .apply {
                setDialogMessage(message = deniedString)
                setPositiveButton("확인") {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + activity.packageName)
                    activity.startActivity(intent)
                    activity.finish()
                }
                setNegativeButton("취소") {
                    Toast.makeText(activity, "취소버튼", Toast.LENGTH_SHORT).show()
                }
                setHeaderImage(R.mipmap.ic_launcher)
                show()
            }
    }
}
