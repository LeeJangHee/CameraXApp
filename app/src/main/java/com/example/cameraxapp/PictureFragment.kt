package com.example.cameraxapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.golf.vct.util.PermissionCheck
import kotlinx.android.synthetic.main.fragment_picture.view.*


class PictureFragment : Fragment() {

    lateinit var permissionCheck:PermissionCheck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionCheck = PermissionCheck(this@PictureFragment, object : PermissionCheck.PermissionListener {
            override fun permissionAllowed() {
                val intent = Intent(requireActivity(), CameraActivity::class.java)
                startActivity(intent)
            }

        })

        view.iv_picture.setOnClickListener {

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
}