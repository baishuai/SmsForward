package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.BuildConfig

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo(private val api: FeigeApi) {

    fun forward(sms: SmsMessage) {
        BuildConfig.FEIGE_SECRET
    }
}