package baishuai.github.io.smsforward.forward.directsms

import android.content.Context
import android.telephony.SmsMessage
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single

/**
 * Created by bai on 17-6-17.
 */
class DirectSmsRepo(private val context: Context,
                    private val number: String) : ForwardRepoApi {
    override fun forward(sms: SmsMessage): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun name(): String {
        return "direct sms"
    }

    override fun checkTokenAndSave(): Single<Boolean> {
        // check if valid number
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val DIRECT_SMS = this::class.java.canonicalName + "DIRECT_SMS"
    }
}