package baishuai.github.io.smsforward.injection.module

import android.app.Activity
import android.content.Context
import baishuai.github.io.smsforward.injection.ActivityContext
import dagger.Module
import dagger.Provides


/**
 * Created by bai on 17-4-29.
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    fun providesContext(): Context {
        return activity
    }
}