package app.x.call.floating.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import app.x.call.floating.receivers.CallReceiver

class CallService : Service() {

    lateinit var filter: IntentFilter
    lateinit var callReceiver: CallReceiver

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")

        callReceiver = CallReceiver()
        registerReceiver(callReceiver, filter)

        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(callReceiver)
        super.onDestroy()
    }
}
