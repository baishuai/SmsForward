package baishuai.github.io.smsforward.forward

import android.telephony.SmsMessage
import baishuai.github.io.smsforward.IApp
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

    init {
        //repos.add(context.applicationComponent.forwardComponemt().feigeRepo())
        repos.add(context.applicationComponent.forwardComponemt().slackRepo())
    }

    fun forward(sms: SmsMessage) {
        repos.forEach {
            it.forward(sms)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ Timber.d(it.toString() + " Forward Success") }, { Timber.e(it) })
        }
    }

}