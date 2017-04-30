package baishuai.github.io.smsforward

import android.app.Application
import android.content.Context
import baishuai.github.io.smsforward.injection.component.ApplicationComponent
import baishuai.github.io.smsforward.injection.component.DaggerApplicationComponent
import baishuai.github.io.smsforward.injection.module.ApplicationModule
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by bai on 17-4-29.
 */
class IApp : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()

        Timber.plant(DebugTree())
    }


    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        operator fun get(context: Context): IApp {
            return context.applicationContext as IApp
        }
    }
}

