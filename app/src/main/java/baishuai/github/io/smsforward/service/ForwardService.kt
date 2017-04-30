package baishuai.github.io.smsforward.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.Telephony
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.MainActivity
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.forward.FeigeRepo
import javax.inject.Inject

/**
 * Created by bai on 17-4-29.
 */


class ForwardService : Service() {

    @Inject
    lateinit var forward: FeigeRepo

    override fun onCreate() {
        super.onCreate()
        IApp[this].applicationComponent.inject(this)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = Notification.Builder(this)
                .setContentTitle(getString(R.string.forward_service))
                .setContentText(getString(R.string.forward_service))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.forward_service))
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                forward.forward(smsMessage)
            }
        }
        return START_REDELIVER_INTENT
    }

}