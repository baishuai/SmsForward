package baishuai.github.io.smsforward.forward


import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by bai on 17-4-29.
 */
interface FeigeApi {

    @POST("api/user_sendmsg")
    fun forward(@Query("key") key: String,
                @Query("title") title: String,
                @Query("content") content: String,
                @Query("remark") remark: String): Call<Result>
}