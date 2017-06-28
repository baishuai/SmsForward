package baishuai.github.io.smsforward.forward.feige

import android.content.Context
import android.preference.PreferenceManager
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import io.reactivex.Single

/**
 * Created by bai on 17-4-29.
 */
class FeigeRepo(private val context: Context,
                private val api: FeigeApi,
                private val token: String,
                private val uid: String) : ForwardRepoApi {

    override fun name(): String {
        return "feige"
    }

    override fun forward(body: String, from: String): Single<Boolean> {
        return api.forward(uid, token, "status",
                body.substring(0, minOf(6, body.length)), from, body)
                .retry(2).map { it.code == 200 }
    }

    override fun checkTokenAndSave(): Single<Boolean> {
        return api.forward(uid, token, "status", "token check", "token check", "token check")
                .map { it.code == 200 }
                .doOnSuccess {
                    if (it) {
                        PreferenceManager.getDefaultSharedPreferences(context).edit()
                                .putString(FEIGE_TOKEN, token)
                                .putString(FEIGE_UID, uid)
                                .apply()
                    }
                }
    }


    companion object {
        val FEIGE_TOKEN = this::class.java.canonicalName + "FEIGE_TOKEN"
        val FEIGE_UID = this::class.java.canonicalName + "FEIGE_UID"
    }
}