package baishuai.github.io.smsforward.injection.component

import baishuai.github.io.smsforward.MainActivity
import baishuai.github.io.smsforward.injection.ActivityScope
import baishuai.github.io.smsforward.injection.module.ActivityModule
import dagger.Component

/**
 * Created by bai on 17-4-29.
 */


@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}