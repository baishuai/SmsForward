package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo @Inject constructor(private val api: FeigeApi) {

    fun forward(sms: SmsMessage) {
        val call = api.forward("status", sms.displayMessageBody.substring(0, minOf(6, sms.displayMessageBody.length)),
                sms.originatingAddress, sms.messageBody)
        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
            }
        })
    }
}