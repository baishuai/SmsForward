package baishuai.github.io.smsforward.ui.base

import android.support.v7.app.AppCompatActivity
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.injection.component.ActivityComponent
import baishuai.github.io.smsforward.injection.component.DaggerActivityComponent
import baishuai.github.io.smsforward.injection.module.ActivityModule

/**
 * Created by bai on 17-4-30.
 */
open class BaseActivity : AppCompatActivity() {

    var mActivityComponent: ActivityComponent? = null
        get() {
            if (field == null) {
                field = DaggerActivityComponent.builder()
                        .applicationComponent(IApp[this].applicationComponent)
                        .activityModule(ActivityModule(this))
                        .build()
            }
            return field
        }

}