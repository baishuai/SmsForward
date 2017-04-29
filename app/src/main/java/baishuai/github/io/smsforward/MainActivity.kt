package baishuai.github.io.smsforward

import android.Manifest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import baishuai.github.io.smsforward.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val isGranted = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        mainBinding.permissionSwitch.isChecked = isGranted

        if (isGranted) {
            mainBinding.permissionSwitch.text = getString(R.string.permission_granted)
            mainBinding.permissionSwitch.isEnabled = false
        } else {
            mainBinding.permissionSwitch.text = getString(R.string.request_sms_permission)
            mainBinding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.RECEIVE_SMS), REQUEST_SMS_RECEIVE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_SMS_RECEIVE -> {
                Log.d(this.javaClass.canonicalName, grantResults.toString())
                val idx = Arrays.asList(*permissions).indexOf(Manifest.permission.RECEIVE_SMS)
                mainBinding.permissionSwitch.isChecked = grantResults[idx] == PackageManager.PERMISSION_GRANTED
                if (mainBinding.permissionSwitch.isChecked) {
                    mainBinding.permissionSwitch.isEnabled = false
                    mainBinding.permissionSwitch.setOnCheckedChangeListener(null)
                }
            }
        }
    }

    companion object {

        private val REQUEST_SMS_RECEIVE = 10010
    }
}
