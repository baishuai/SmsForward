package baishuai.github.io.smsforward.forward.feige

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo @Inject constructor(private val api: FeigeApi) : ForwardRepoApi {

    override fun forward(sms: SmsMessage) {
        val call = api.forward(BuildConfig.FEIGE_UID.toString(),
                BuildConfig.FEIGE_SECRET,
                "status",
                sms.displayMessageBody.substring(0, minOf(6, sms.displayMessageBody.length)),
                sms.originatingAddress,
                sms.messageBody)
        call.enqueue(object : Callback<FeigeResult> {
            override fun onFailure(call: Call<FeigeResult>, t: Throwable) {
                Timber.d(t)
            }

            override fun onResponse(call: Call<FeigeResult>, response: Response<FeigeResult>) {
                Timber.d(response.body().toString())
            }
        })
    }
}