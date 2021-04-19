package com.golf.vct.util

import android.Manifest

class Constants {

    companion object {

        // 권한
        const val PERMISSION_CODE_OK = 1004
        val PERMISSION_REQUESTS = arrayOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
        )
        val PERMISSION_MAPS = mapOf<String, String>(
            "android.permission.WRITE_EXTERNAL_STORAGE" to "파일",
            "android.permission.CAMERA" to "카메라",
            "android.permission.ACCESS_FINE_LOCATION" to "위치",
            "android.permission.READ_PHONE_STATE" to "전화"
        )

        val PERMISSION_CAMERA = Manifest.permission.CAMERA

    }
}