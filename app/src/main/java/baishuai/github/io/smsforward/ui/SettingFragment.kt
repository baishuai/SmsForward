package baishuai.github.io.smsforward.ui

import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.databinding.FragmentSettingBinding
import baishuai.github.io.smsforward.forward.feige.FeigeRepo
import baishuai.github.io.smsforward.forward.slack.SlackRepo
import baishuai.github.io.smsforward.service.ForwardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


/**
 * Created by bai on 17-6-17.
 */
class SettingFragment : Fragment(), CompoundButton.OnCheckedChangeListener {


    lateinit var mBinding: FragmentSettingBinding
    var mProgress: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        mBinding.cbSlack.setOnCheckedChangeListener(this)
        mBinding.cbFeige.setOnCheckedChangeListener(this)
        return mBinding.root
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        //todo hide soft keyboard
        // todo use dialog
//        val builder = AlertDialog.Builder(activity)
//        // Get the layout inflater
//        val inflater = activity.layoutInflater
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(inflater.inflate(R.layout.dialog_token, null))
//                // Add action buttons
//                .setPositiveButton(R.string.confirm, { dialog, id ->
//                    // sign in the user ...
//
//                })
//                .setNegativeButton(R.string.cancel, { dialog, id -> dialog.dismiss() })
//        builder.create().show()

        val repo_token = if (buttonView.id == R.id.cb_slack) {
            SlackRepo.SLACK_TOKEN
        } else FeigeRepo.FEIGE_TOKEN

        val repo_channel = if (buttonView.id == R.id.cb_slack) {
            SlackRepo.SLACK_CHANNEL
        } else FeigeRepo.FEIGE_UID


        if (isChecked && buttonView.id != R.id.cb_send_sms) {

            val token = if (buttonView.id == R.id.cb_slack) {
                mBinding.etSlackToken.text.toString()
            } else mBinding.etFeigeToken.text.toString()
            val channel = if (buttonView.id == R.id.cb_slack) {
                mBinding.etSlackChannel.text.toString()
            } else mBinding.etFeigeUid.text.toString()


            val keys = mapOf(Pair(repo_token, token), Pair(repo_channel, channel))

            val repo = if (buttonView.id == R.id.cb_slack) {
                SlackRepo(IApp[context].applicationComponent.forwardComponemt().slackApi(), token, channel)
            } else FeigeRepo(IApp[context].applicationComponent.forwardComponemt().feigeApi(), token, channel)



            if (token.trim().isEmpty() || channel.trim().isEmpty()) {
                Toast.makeText(context, "fill the empty", Toast.LENGTH_SHORT).show()
                buttonView.isChecked = false
            } else {
                mProgress = ProgressDialog.show(context, "check token", "")

                repo.checkToken()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (!it) {
                                buttonView.isChecked = false
                            }
                            onCompleted(it, keys)

                        }, { Timber.e(it) })
            }

        } else if (!isChecked) {

        }
    }

    fun onCompleted(result: Boolean, keys: Map<String, String>) {
        if (result) {
            keys.forEach { t, u ->
                PreferenceManager.getDefaultSharedPreferences(context).edit()
                        .putString(t, u)
                        .apply()
            }

            val serviceIntent = Intent(activity, ForwardService::class.java)
            serviceIntent.action = UPDATE_REPOS
            context.startService(serviceIntent)

            Toast.makeText(context, "check token succeed", Toast.LENGTH_LONG).show()
        }
        mProgress?.dismiss()
    }

    companion object {
        val UPDATE_REPOS = this::class.java.canonicalName + "UPDATE_REPOS"
    }
}

