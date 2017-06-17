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

        mainBinding.toolbar.title = getString(R.string.app_name)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, MainFragment()).commit()

        mainBinding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_settings) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, PrefsFragment())
                        .addToBackStack("main_fragment")
                        .commit()
                true
            } else {
                false
            }
        }
    }

    fun clearMenu() {
        mainBinding.toolbar.menu.clear()
    }

    fun setMemu() {
        clearMenu()
        mainBinding.toolbar.inflateMenu(R.menu.menu_main)
    }

}
