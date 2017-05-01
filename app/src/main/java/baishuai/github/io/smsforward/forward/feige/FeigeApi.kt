package baishuai.github.io.smsforward.forward.feige


import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by bai on 17-4-29.
 */
interface FeigeApi {

    @POST("api/user_sendmsg")
    fun forward(@Query("uid") uid: String,
                @Query("secret") secret: String,
                @Query("key") key: String,
                @Query("title") title: String,
                @Query("content") content: String,
                @Query("remark") remark: String): Single<FeigeResult>
}