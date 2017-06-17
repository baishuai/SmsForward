package baishuai.github.io.smsforward.forward.slack

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single

/**
 * Created by bai on 17-5-1.
 */

class SlackRepo(private val api: SlackApi, private val token: String, private val channel: String) : ForwardRepoApi {

    override fun name(): String {
        return "slack"
    }

    override fun forward(sms: SmsMessage): Single<Boolean> {
        return api.forward(token, channel,
                sms.messageBody,
                sms.originatingAddress, false)
                .retry(2).map { it.ok }
    }

    override fun checkToken(): Single<Boolean> {
        return api.forward(token, channel, "test token", "", false).map { it.ok }
    }


    companion object {
        val SLACK_TOKEN = this::class.java.canonicalName + "SLACK_TOKEN"
        val SLACK_CHANNEL = this::class.java.canonicalName + "SLACK_CHANNEL"
    }
}