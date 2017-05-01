package baishuai.github.io.smsforward.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
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
        }
    }
}
