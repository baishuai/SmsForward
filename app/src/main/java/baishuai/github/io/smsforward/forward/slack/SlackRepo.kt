package baishuai.github.io.smsforward.forward.slack

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bai on 17-5-1.
 */

class SlackRepo @Inject constructor(private val api: SlackApi) : ForwardRepoApi {

    override fun forward(sms: SmsMessage) {
        val call = api.forward(BuildConfig.SLACK_TOKEN, BuildConfig.SLACK_CHANNEL,
                sms.messageBody,
                sms.originatingAddress, false)

        call.enqueue(object : Callback<SlackResult> {
            override fun onResponse(call: Call<SlackResult>, response: Response<SlackResult>) {
                Timber.d(response.body().toString())
            }

            override fun onFailure(call: Call<SlackResult>, t: Throwable) {
                Timber.d(t)
            }
        })
    }
}