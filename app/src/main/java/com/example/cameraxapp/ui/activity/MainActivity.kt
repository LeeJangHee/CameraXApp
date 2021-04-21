package com.example.cameraxapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cameraxapp.R
import com.example.cameraxapp.ui.fragment.AlbumFragment
import com.example.cameraxapp.ui.fragment.PictureFragment
import com.example.cameraxapp.util.Constants.Companion.ALBUM_OK
import com.example.cameraxapp.util.Constants.Companion.TAG
import com.example.cameraxapp.util.PermissionCheck
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var permissionCheck: PermissionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionCheck = PermissionCheck(this, object : PermissionCheck.PermissionListener {
            override fun permissionAllowed() {
//                val intent = Intent(this@MainActivity, CameraActivity::class.java)
//                startActivity(intent)
            }

        })

        btn_album.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, AlbumFragment())
                .commit()
        }
        btn_picture.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, PictureFragment())
                .commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "Activity: $requestCode, $resultCode, $data")

        when (resultCode) {
            ALBUM_OK -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, AlbumFragment()).commit()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionCheck.onRequestPermissionResult(requestCode, permissions, grantResults)
    }
}