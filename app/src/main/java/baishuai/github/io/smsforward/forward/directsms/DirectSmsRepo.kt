package baishuai.github.io.smsforward.forward.directsms

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by bai on 17-6-17.
 */
class DirectSmsRepo @Inject constructor() : ForwardRepoApi {
    override fun forward(sms: SmsMessage): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun name(): String {
        return "direct sms"
    }

    override fun checkToken(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val DIRECT_SMS = this::class.java.canonicalName + "DIRECT_SMS"
    }
}