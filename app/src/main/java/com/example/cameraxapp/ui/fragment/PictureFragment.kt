package com.example.cameraxapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cameraxapp.R
import com.example.cameraxapp.ui.activity.CameraActivity
import com.example.cameraxapp.ui.activity.MainActivity
import com.example.cameraxapp.util.Constants.Companion.ALBUM_OK
import com.example.cameraxapp.util.Constants.Companion.PERMISSION_CAMERA
import com.example.cameraxapp.util.Constants.Companion.TAG
import com.example.cameraxapp.util.PermissionCheck
import kotlinx.android.synthetic.main.fragment_picture.view.*


class PictureFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var permissionCheck: PermissionCheck

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = MainActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionCheck =
            PermissionCheck(this@PictureFragment, object : PermissionCheck.PermissionListener {
                override fun permissionAllowed() {
//                mainActivity.openCameraActivity()
                    val intent = Intent(activity, CameraActivity::class.java)

                    // requestCode 값으로 안드로이드 값을 넣으면 안된다.
                    activity?.startActivityForResult(intent, ALBUM_OK)
                }

            })

        view.iv_picture.setOnClickListener {
            permissionCheck.hasPermissions(arrayListOf(PERMISSION_CAMERA))
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionCheck.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "Fragment: $requestCode, $resultCode, $data")
    }
}