package baishuai.github.io.smsforward.forward

import android.preference.PreferenceManager
import android.telephony.SmsMessage
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.forward.directsms.DirectSmsRepo
import baishuai.github.io.smsforward.forward.feige.FeigeRepo
import baishuai.github.io.smsforward.forward.slack.SlackRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bai on 17-5-1.
 */

@Singleton
class ForwardRepo @Inject constructor(val context: IApp) {

    private val repos = ArrayList<ForwardRepoApi>()
    private var via_sms = false

    init {
        updateRepos()
    }

    fun updateRepos() {
        repos.clear()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        if (sharedPreferences.getBoolean(context.getString(R.string.slack_checkbox_preference), false)) {
            val slack_token = sharedPreferences.getString(SlackRepo.SLACK_TOKEN, "")
            val slack_channel = sharedPreferences.getString(SlackRepo.SLACK_CHANNEL, "")
            if (slack_token.isNotEmpty() && slack_channel.isNotEmpty()) {
                val api = context.applicationComponent.forwardComponemt().slackApi()
                repos.add(SlackRepo(context, api, slack_token, slack_channel))
            }
        }

        if (sharedPreferences.getBoolean(context.getString(R.string.feige_checkbox_preference), false)) {
            val feige_token = sharedPreferences.getString(FeigeRepo.FEIGE_TOKEN, "")
            val feige_uid = sharedPreferences.getString(FeigeRepo.FEIGE_UID, "")
            if (feige_token.isNotEmpty() && feige_uid.isNotEmpty()) {
                val api = context.applicationComponent.forwardComponemt().feigeApi()
                repos.add(FeigeRepo(context, api, feige_token, feige_uid))
            }
        }

        via_sms = sharedPreferences.getBoolean(context.getString(R.string.direct_sms_checkbox_preference), false)
    }

    fun forwardSms(sms: Array<SmsMessage>) {
        val body = sms.fold(StringBuilder()) { acc, s -> acc.append(s.displayMessageBody) }.trim().toString()
        val from = if (sms.any()) sms.first().originatingAddress else "empty sms"
        forward(body, from)
    }

    fun forwardMissCall(number: String) {
        forward("您有来自" + number + "未接电话", number)
    }

    private fun forward(body: String, from: String) {
        repos.forEach {
            it.forward(body, from)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ Timber.d(it.toString() + " Forward Success") }, { onError(it, body, from) })
        }
    }

    fun onError(t: Throwable, body: String, from: String) {
        if (via_sms) {
            DirectSmsRepo(context, "todo number").forward(body, from)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ Timber.d(it.toString()) }, { Timber.d(it) })
        }
    }
}