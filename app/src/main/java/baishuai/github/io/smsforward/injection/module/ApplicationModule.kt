package baishuai.github.io.smsforward.injection.module

import android.app.Application
import android.content.Context
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.forward.FeigeApi
import baishuai.github.io.smsforward.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit


/**
 * Created by bai on 17-4-29.
 */
@Module
class ApplicationModule(private val app: IApp) {

    @Provides
    fun provideIApp(): IApp {
        return app
    }

    @Provides
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return app.applicationContext
    }


    @Provides
    fun provideOkhttp(): OkHttpClient {

        val build = OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().addQueryParameter("uid", "159")
                    .addQueryParameter("secret", BuildConfig.FEIGE_SECRET)
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            build.addInterceptor(logging)
        }
        return build.build()
    }


    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://u.ifeige.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }

    @Provides
    fun provideFeigeApi(retrofit: Retrofit): FeigeApi {
        return retrofit.create(FeigeApi::class.java)
    }
}