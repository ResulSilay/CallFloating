package app.x.call.floating.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import app.x.call.floating.R
import app.x.call.floating.Utilty
import app.x.call.floating.models.Person

class InfoViewService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var viewWidgetInfo: View
    private lateinit var txtPersonName: TextView
    private lateinit var person: Person

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var phoneNumber = intent?.extras?.get("phoneNumber")

        if (phoneNumber.toString().isNotEmpty()) {

            this.person = Utilty.getPersonInfo(applicationContext, phoneNumber.toString())
            txtPersonName.text = person.DisplayName
        } else {
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()

        viewWidgetInfo = LayoutInflater.from(applicationContext).inflate(R.layout.widget_info, null)

        var layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP and Gravity.LEFT
        layoutParams.x = 0
        layoutParams.y = 100

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(viewWidgetInfo, layoutParams)

        txtPersonName = viewWidgetInfo.findViewById(R.id.txt_person_name)
        txtPersonName.text = "DENEMEEE"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewWidgetInfo != null)
            windowManager.removeView(viewWidgetInfo)
    }
}