package baishuai.github.io.smsforward.forward.slack

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by bai on 17-5-1.
 */

class SlackRepo @Inject constructor(private val api: SlackApi) : ForwardRepoApi {

    override fun forward(sms: SmsMessage): Single<Boolean> {
        return api.forward(BuildConfig.SLACK_TOKEN, BuildConfig.SLACK_CHANNEL,
                sms.messageBody,
                sms.originatingAddress, false)
                .retry(3).map { it.ok }
    }
}