package baishuai.github.io.smsforward.forward

import io.reactivex.Single

/**
 * Created by bai on 17-5-1.
 */
interface ForwardRepoApi {
    fun forward(body: String, from: String): Single<Boolean>
    fun checkTokenAndSave(): Single<Boolean>
    fun name(): String
}


