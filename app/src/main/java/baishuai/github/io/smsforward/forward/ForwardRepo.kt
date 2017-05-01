package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.IApp
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bai on 17-5-1.
 */

@Singleton
class ForwardRepo @Inject constructor(val context: IApp) {

    private val repos = ArrayList<ForwardRepoApi>()

    init {
        repos.add(context.applicationComponent.forwardComponemt().feigeRepo())
        repos.add(context.applicationComponent.forwardComponemt().slackRepo())
    }

    fun forward(sms: SmsMessage) {
        repos.forEach { it.forward(sms) }
    }

}