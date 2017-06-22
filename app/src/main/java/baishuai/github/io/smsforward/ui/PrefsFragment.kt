package baishuai.github.io.smsforward.ui

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import android.widget.Toast
import baishuai.github.io.smsforward.IApp
import baishuai.github.io.smsforward.R
import baishuai.github.io.smsforward.databinding.DialogTokenBinding
import baishuai.github.io.smsforward.forward.ForwardRepoApi
import baishuai.github.io.smsforward.forward.directsms.DirectSmsRepo
import baishuai.github.io.smsforward.forward.feige.FeigeRepo
import baishuai.github.io.smsforward.forward.slack.SlackRepo
import baishuai.github.io.smsforward.service.ForwardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by bai on 17-6-17.
 */

class PrefsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey)
        if (activity is MainActivity) {
            (activity as MainActivity).clearMenu()
        }

        // todo , implement sms
        findPreference(getString(R.string.direct_sms_checkbox_preference))?.isEnabled = false
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        Timber.d(preference.key)
        if (preference is CheckBoxPreference) {
            if (preference.isChecked) {
                preference.isChecked = false
                val builder = AlertDialog.Builder(activity)
                // Get the layout inflater
                val binding = DataBindingUtil.inflate<DialogTokenBinding>(activity.layoutInflater,
                        R.layout.dialog_token, null, false)
                setupBinding(binding, preference.key)
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(binding.root)
                        .setPositiveButton(R.string.confirm, { dialog, _ -> onClick(dialog, binding, preference) })
                        .setNegativeButton(R.string.cancel, { dialog, _ ->
                            run {
                                preference.isChecked = false
                                dialog.dismiss()
                            }
                        })
                builder.create().show()
            } else {
                updateRepos()
            }
        }
        return true //super.onPreferenceTreeClick(preference)
    }

    fun updateRepos() {
        val serviceIntent = Intent(activity, ForwardService::class.java)
        serviceIntent.action = UPDATE_REPOS
        context.startService(serviceIntent)
    }

    fun onClick(dialog: DialogInterface, binding: DialogTokenBinding, preference: CheckBoxPreference) {
        dialog.dismiss()
        val progress = ProgressDialog.show(context, "check token", "")
        val channel = binding.channel.text.toString()
        val token = binding.token.text.toString()
        val repo: ForwardRepoApi =
                when (preference.key) {
                    getString(R.string.slack_checkbox_preference) -> {
                        SlackRepo(context, IApp[context].applicationComponent.forwardComponemt().slackApi(), token, channel)
                    }
                    getString(R.string.feige_checkbox_preference) -> {
                        FeigeRepo(context, IApp[context].applicationComponent.forwardComponemt().feigeApi(), token, channel)
                    }
                    else -> {
                        DirectSmsRepo(context, channel)
                    }
                }
        repo.checkTokenAndSave()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { progress.dismiss() }
                .subscribe({
                    if (!it) {
                        Toast.makeText(context, getString(R.string.unvalid_config), Toast.LENGTH_LONG).show()
                    } else {
                        preference.isChecked = true
                        updateRepos()
                    }
                }, {
                    preference.isChecked = false
                    Timber.e(it)
                })
    }


    fun setupBinding(binding: DialogTokenBinding, key: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        when (key) {
            getString(R.string.slack_checkbox_preference) -> {
                binding.token.setText(sharedPreferences.getString(SlackRepo.SLACK_TOKEN, ""))
                binding.token.hint = getString(R.string.slack_token)
                binding.channel.setText(sharedPreferences.getString(SlackRepo.SLACK_CHANNEL, ""))
                binding.channel.hint = getString(R.string.slack_channel)
            }
            getString(R.string.feige_checkbox_preference) -> {
                binding.token.setText(sharedPreferences.getString(FeigeRepo.FEIGE_TOKEN, ""))
                binding.token.hint = getString(R.string.feige_secret)
                binding.channel.setText(sharedPreferences.getString(FeigeRepo.FEIGE_UID, ""))
                binding.channel.hint = getString(R.string.feige_uid)
            }
            getString(R.string.direct_sms_checkbox_preference) -> {
                binding.channel.hint = getString(R.string.phone_number)
                binding.token.visibility = View.GONE
            }
        }
    }

    companion object {
        val UPDATE_REPOS = this::class.java.canonicalName + "UPDATE_REPOS"
    }
}