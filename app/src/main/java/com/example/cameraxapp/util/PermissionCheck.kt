package com.example.cameraxapp.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cameraxapp.util.Constants.Companion.PERMISSION_CODE_OK
import com.example.cameraxapp.util.Constants.Companion.PERMISSION_MAPS

class PermissionCheck {
    var activity: Activity? = null
    var fragment: Fragment? = null
    var permissionListener: PermissionListener


    constructor(activity: Activity, permissionListener: PermissionListener) {
        this.activity = activity
        this.permissionListener = permissionListener
    }

    constructor(fragment: Fragment, permissionListener: PermissionListener) {
        this.fragment = fragment
        this.activity = fragment.activity!!
        this.permissionListener = permissionListener
    }

    // selfcheck
    fun hasPermissions(permissionList: ArrayList<String>) {
        val checkPermissionList = arrayListOf<String>()
        for (permission in permissionList) {
            if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
                // 권한 없음
                checkPermissionList.add(permission)
            }
        }

        if (checkPermissionList.isEmpty()) {
            permissionListener.permissionAllowed()
            return
        }

        if (checkPermissionList.isEmpty()) {
            permissionListener.permissionAllowed()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(checkPermissionList)
            }
        }

    }

    // 권한 요청
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission(checkPermissionList: ArrayList<String>) {
        if (fragment != null) {
            fragment?.requestPermissions(checkPermissionList.toTypedArray(), PERMISSION_CODE_OK)
        } else {
            activity?.requestPermissions(checkPermissionList.toTypedArray(), PERMISSION_CODE_OK)
        }
    }

    // permission
    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE_OK) {
            val deniedPermissionList = ArrayList<String>()
            for (i in 0 until permissions.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    // 거절
                    deniedPermissionList.add(PERMISSION_MAPS[permissions[i]].toString())
                }
            }

            if (deniedPermissionList.isEmpty()) {
                // 모두 수락
                permissionListener.permissionAllowed()
            } else {
                // 거절했음
                permissionListener.permissionDenied(activity!!, deniedPermissionList)
            }

        }
    }

    interface PermissionListener {
        fun permissionAllowed()
        fun permissionDenied(activity: Activity,deniedPermissions: ArrayList<String>) {
            DialogUtil().showDialogPermissionCheck(activity, deniedPermissions)
        }
    }

}