package baishuai.github.io.smsforward.injection.component

import android.app.Application
import android.content.Context
import baishuai.github.io.smsforward.injection.ApplicationContext
import baishuai.github.io.smsforward.injection.module.ApplicationModule
import baishuai.github.io.smsforward.service.ForwardService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bai on 17-4-29.
 */

//@ApplicationScope
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(forwardService: ForwardService)

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}