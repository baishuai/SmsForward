package baishuai.github.io.smsforward.forward.feige

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo(private val api: FeigeApi, private val token: String, private val uid: String) : ForwardRepoApi {

    override fun name(): String {
        return "feige"
    }

    override fun forward(sms: SmsMessage): Single<Boolean> {
        return api.forward(uid, token, "status",
                sms.displayMessageBody.substring(0, minOf(6, sms.displayMessageBody.length)),
                sms.originatingAddress,
                sms.messageBody)
                .retry(2).map { it.code == 200 }
    }

    override fun checkToken(): Single<Boolean> {
        return api.forward(uid, token, "status", "token check", "token check", "token check").map { it.code == 200 }
    }


    companion object {
        val FEIGE_TOKEN = this::class.java.canonicalName + "FEIGE_TOKEN"
        val FEIGE_UID = this::class.java.canonicalName + "FEIGE_UID"
    }
}