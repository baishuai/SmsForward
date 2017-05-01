package baishuai.github.io.smsforward.forward

import baishuai.github.io.smsforward.forward.feige.FeigeApi
import baishuai.github.io.smsforward.forward.slack.SlackApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by bai on 17-5-1.
 */

@Module
class ForwardModule {

    @Provides
    @Named("feige")
    fun provideFeigeRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://u.ifeige.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }


    @Provides
    fun provideFeigeApi(@Named("feige") retrofit: Retrofit): FeigeApi {
        return retrofit.create(FeigeApi::class.java)
    }


    @Provides
    @Named("slack")
    fun provideSlackRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://slack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }


    @Provides
    fun provideSlackApi(@Named("slack") retrofit: Retrofit): SlackApi {
        return retrofit.create(SlackApi::class.java)
    }


}