package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import android.util.Log
import java.io.IOException
import javax.inject.Inject

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo @Inject constructor(private val api: FeigeApi) {

    fun forward(sms: SmsMessage) {
        val call = api.forward("status", sms.displayMessageBody.substring(0, minOf(6, sms.displayMessageBody.length)),
                sms.originatingAddress, sms.messageBody)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d(this.javaClass.canonicalName, response.body().code.toString() + response.body().msg)
            }
        } catch (e: IOException) {
            //TODO: forward sms by sending sms directly
            Log.d(this.javaClass.canonicalName, e.toString())
        }
    }
}