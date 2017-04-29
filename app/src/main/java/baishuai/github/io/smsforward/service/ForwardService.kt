package baishuai.github.io.smsforward.service

import android.app.IntentService
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.forward.FeigeRepo
import javax.inject.Inject

/**
 * Created by bai on 17-4-29.
 */


class ForwardService : IntentService("ForwardService") {

    @Inject
    lateinit var forward: FeigeRepo

    override fun onCreate() {
        super.onCreate()
        IApp[this].applicationComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(this.javaClass.canonicalName, "onHandleIntent")
        if (intent == null)
            return
        for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            forward.forward(smsMessage)
        }
    }
}