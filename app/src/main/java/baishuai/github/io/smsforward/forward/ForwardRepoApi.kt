package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import io.reactivex.Single

/**
 * Created by bai on 17-5-1.
 */
interface ForwardRepoApi {
    fun forward(sms: SmsMessage): Single<Boolean>
    fun checkToken(): Single<Boolean>
    fun name(): String

}


