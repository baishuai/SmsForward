package baishuai.github.io.smsforward.forward.feige

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo @Inject constructor(private val api: FeigeApi) : ForwardRepoApi {

    override fun forward(sms: SmsMessage): Single<Boolean> {
        return api.forward(BuildConfig.FEIGE_UID.toString(),
                BuildConfig.FEIGE_SECRET,
                "status",
                sms.displayMessageBody.substring(0, minOf(6, sms.displayMessageBody.length)),
                sms.originatingAddress,
                sms.messageBody)
                .retry(3)
                .map { it.code == 200 }
    }
}