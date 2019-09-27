package app.x.call.floating

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.provider.ContactsContract
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import app.x.call.floating.services.CallService
import android.R.id


class MainActivity : AppCompatActivity() {

    private var CODE_FLOATING_PERMISSION = 2084

    lateinit var txtInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtInfo = findViewById(R.id.txt_service_info)

        Permission()
        Services()
    }

    private fun Services() {
        if (checkPermission()) {
            startService(Intent(applicationContext, CallService::class.java))
            txtInfo.text = "run service";
        }
    }

    private fun checkPermission(): Boolean {
        var permissions =
            arrayListOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS)

        for (permission in permissions) {
            Log.d("Permission", permission)
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }

    private fun Permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS
                ), 0
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this))
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION), CODE_FLOATING_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_FLOATING_PERMISSION) {
            if (resultCode == Activity.RESULT_OK)
                Utilty.Restart(this)
            else {
                Toast.makeText(
                    applicationContext,
                    "Gerekli izinler verilmediği için uygulama başlatılamamıştır.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }
}
