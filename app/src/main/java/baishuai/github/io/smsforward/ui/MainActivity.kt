package baishuai.github.io.smsforward.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.databinding.ActivityMainBinding
import baishuai.github.io.smsforward.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(mainBinding.toolbar)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, MainFragment()).commit()
    }

}
