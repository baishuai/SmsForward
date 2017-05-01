package baishuai.github.io.smsforward.forward.slack

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by bai on 17-5-1.
 */
interface SlackApi {

    @POST("api/chat.postMessage")
    fun forward(@Query("token") token: String,
                @Query("channel") channel: String,
                @Query("text") text: String,
                @Query("username") username: String,
                @Query("as_user") as_user: Boolean): Single<SlackResult>
}