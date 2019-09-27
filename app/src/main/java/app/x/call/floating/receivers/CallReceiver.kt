package app.x.call.floating.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import app.x.call.floating.services.InfoViewService

class CallReceiver : BroadcastReceiver() {

    lateinit var extras: Bundle
    lateinit var intentViewService: Intent

    override fun onReceive(context: Context?, intent: Intent?) {
        extras = intent?.extras!!

        Log.d(context?.packageName, "run callReceviver")
        //Toast.makeText(context, intent.toString(), Toast.LENGTH_LONG).show()

        if (intent?.action.equals("android.intent.action.PHONE_STATE")) {

            var state = extras.get(TelephonyManager.EXTRA_STATE)
            //Toast.makeText(context, state.toString(), Toast.LENGTH_LONG).show()

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                var phoneNumber = extras.get(TelephonyManager.EXTRA_INCOMING_NUMBER)
                //Toast.makeText(context, phoneNumber.toString(), Toast.LENGTH_SHORT).show()

                intentViewService = Intent(context, InfoViewService::class.java)
                intentViewService.putExtra("phoneNumber", phoneNumber.toString())
                context?.startService(intentViewService)
            } else {
                intentViewService = Intent(context, InfoViewService::class.java)
                intentViewService.putExtra("phoneNumber", "")
                context?.startService(intentViewService)
            }
        }
    }
}