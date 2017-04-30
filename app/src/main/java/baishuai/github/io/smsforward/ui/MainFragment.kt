package baishuai.github.io.smsforward.ui

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.databinding.FragmentMainBinding
import baishuai.github.io.smsforward.service.ForwardService
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
class MainFragment : Fragment() {

    lateinit var mBinding: FragmentMainBinding


    var isRegister: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        isRegister = sharedPreferences.getBoolean(REGISTER_RECEIVER, false) &&
                isMyServiceRunning(ForwardService::class.java)
        if (isRegister) {
            mBinding.btnServiceSwitch.text = getString(R.string.turn_off_service)
        } else {
            mBinding.btnServiceSwitch.text = getString(R.string.turn_on_service)
        }

        mBinding.btnServiceSwitch.setOnClickListener { _ ->
            run {
                val serviceIntent = Intent(activity, ForwardService::class.java)
                if (isRegister) {
                    serviceIntent.action = UNREGISTER_RECEIVER
                    mBinding.btnServiceSwitch.text = getString(R.string.turn_on_service)
                    activity.startService(serviceIntent)
                    isRegister = false
                } else {
                    serviceIntent.action = REGISTER_RECEIVER
                    mBinding.btnServiceSwitch.text = getString(R.string.turn_off_service)
                    activity.startService(serviceIntent)
                    isRegister = true
                }
            }
        }

        val isGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        mBinding.permissionSwitch.isChecked = isGranted

        if (isGranted) {
            mBinding.permissionSwitch.text = getString(R.string.permission_granted)
            mBinding.permissionSwitch.isEnabled = false
        } else {
            mBinding.permissionSwitch.text = getString(R.string.request_sms_permission)
            mBinding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECEIVE_SMS), REQUEST_SMS_RECEIVE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_SMS_RECEIVE -> {
                Log.d(this.javaClass.canonicalName, grantResults.toString())
                val idx = Arrays.asList(*permissions).indexOf(Manifest.permission.RECEIVE_SMS)
                mBinding.permissionSwitch.isChecked = grantResults[idx] == PackageManager.PERMISSION_GRANTED
                if (mBinding.permissionSwitch.isChecked) {
                    mBinding.permissionSwitch.isEnabled = false
                    mBinding.permissionSwitch.setOnCheckedChangeListener(null)
                }
            }
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
    }

    companion object {
        private val REQUEST_SMS_RECEIVE = 10010
        val REGISTER_RECEIVER = this::class.java.canonicalName + "registerReceiver"
        val UNREGISTER_RECEIVER = this::class.java.canonicalName + "unregisterReceiver"
    }
}
