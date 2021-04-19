package com.example.cameraxapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.golf.vct.util.PermissionCheck
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var permissionCheck: PermissionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionCheck = PermissionCheck(this, object : PermissionCheck.PermissionListener {
            override fun permissionAllowed() {
                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                startActivity(intent)
            }

        })

        supportFragmentManager.beginTransaction().replace(R.id.fragment, PictureFragment()).commit()


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