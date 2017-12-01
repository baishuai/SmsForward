package baishuai.github.io.smsforward.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.TelephonyManager
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bai on 17-4-28.
 */

class InSmsListener @Inject constructor() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive " + intent.action)
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val startIntent = Intent(context, ForwardService::class.java)
            startIntent.action = Telephony.Sms.Intents.SMS_RECEIVED_ACTION
            startIntent.putExtras(intent)
            context.startService(startIntent)
        } else if (TelephonyManager.ACTION_PHONE_STATE_CHANGED == intent.action) {
            val telMgr = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            when (telMgr.callState) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    val startIntent = Intent(context, ForwardService::class.java)
                    startIntent.action = TelephonyManager.ACTION_PHONE_STATE_CHANGED
                    startIntent.putExtras(intent)
                    context.startService(startIntent)
                }
            }
        }
    }
}
