package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage

/**
 * Created by bai on 17-5-1.
 */
interface ForwardRepoApi {
    fun forward(sms: SmsMessage)
}