package baishuai.github.io.smsforward.injection.module

import android.app.Application
import android.content.Context
import baishuai.github.io.smsforward.BuildConfig
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


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
        val build = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            build.addInterceptor(logging)
        }
        return build.build()
    }

}