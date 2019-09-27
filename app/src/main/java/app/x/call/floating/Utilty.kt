package app.x.call.floating

import android.app.Activity
import android.app.Person
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract

object Utilty {

    fun getPersonInfo(context: Context, phoneNumber: String): app.x.call.floating.models.Person {

        var person = app.x.call.floating.models.Person()
        person.PhoneNumber = phoneNumber.replace("+9", "")

        var cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.NUMBER + " like ? ", arrayOf("%" + person.PhoneNumber + "%"),
            null
        )

        //Note: like ? %phoneNumber%

        while (cursor.moveToNext()) {
            person.ContactId =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
            person.DisplayName =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        }

        cursor.close()

        return person
    }

    fun Restart(activity: Activity) {
        activity.startActivity(
            activity.baseContext.packageManager.getLaunchIntentForPackage(activity.getBaseContext().getPackageName())!!.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        )
    }
}