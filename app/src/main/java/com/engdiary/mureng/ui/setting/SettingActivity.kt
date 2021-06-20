package com.engdiary.mureng.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.constant.IntentKey.OPEN_SOURCE
import com.engdiary.mureng.constant.IntentKey.PRIVACY_POLICY
import com.engdiary.mureng.constant.IntentKey.TERMS
import com.engdiary.mureng.constant.URLConstant.INSAT_GRAM_URL
import com.engdiary.mureng.data.PushAlertSetting
import com.engdiary.mureng.databinding.ActivitySettingBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.login.LoginActivity
import com.engdiary.mureng.ui.push_alert.PushAlertActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.imagebuttonSettingBack.setOnClickListener { finish() }
        binding.textviewSettingMaker.setOnClickListener {
            Intent(this, DevelopPersonActivity::class.java)
                .also { startActivity(it) }
        }
        initPushAlert(binding.textviewSettingPushalert)
        initLogout(binding.textviewSettingLogout)
        initWithdrawal(binding.textviewSettingWithdraw)
        initLicense(binding.textviewSettingLicense)
        initInstaGram(binding.textviewSettingInstagram)
        initPrivacyPolicy(binding.textviewSettingPrivacyPolicy)
        initTerms(binding.textviewSettingTerms)

        subscribeUi()
    }

    private fun initTerms(textviewSettingTerms: TextView) {
        textviewSettingTerms.setOnClickListener {
            Intent(this, WebviewActivity::class.java)
                .putExtra("mode", TERMS)
                .also { startActivity(it) }
        }
    }

    private fun initLicense(textviewSettingLicense: TextView) {
        textviewSettingLicense.setOnClickListener {
            Intent(this, WebviewActivity::class.java).also {
                it.putExtra("mode", OPEN_SOURCE)
                startActivity(it)
            }
        }
    }

    private fun initInstaGram(textviewSettingLicense: TextView) {
        textviewSettingLicense.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(INSAT_GRAM_URL)).also {
                startActivity(it)
            }
        }
    }

    private fun initPushAlert(textviewSettingPushalert: TextView) {
        textviewSettingPushalert.setOnClickListener {
            viewModel.navigateToPushAlert()
        }
    }

    private fun initLogout(textviewSettingLogout: TextView) {
        textviewSettingLogout.setOnClickListener { showLogoutDialog(this) }
    }

    private fun initWithdrawal(textviewSettingWithdraw: TextView) {
        textviewSettingWithdraw.setOnClickListener { showWithdrawalDialog(this) }
    }

    private fun initPrivacyPolicy(textviewSettingPrivacyPolicy: TextView) {
        textviewSettingPrivacyPolicy.setOnClickListener {
            Intent(this, WebviewActivity::class.java)
                .putExtra("mode", PRIVACY_POLICY)
                .also { startActivity(it) }
        }
    }

    private fun showLogoutDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.setting_logout_dialog_title))
            .setPositiveButton(resources.getString(R.string.setting_logout_dialog_accept)) { dialog, _ ->
                viewModel.expireAccessToken()
                navigateToLoginActivity()
            }
            .setNegativeButton(resources.getString(R.string.setting_logout_dialog_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLoginActivity() {
        Intent(this, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .also { startActivity(it) }
    }

    private fun showWithdrawalDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.setting_withdrawal_dialog_title))
            .setMessage(resources.getString(R.string.setting_withdrawal_dialog_message))
            .setPositiveButton(resources.getString(R.string.setting_withdrawal_dialog_accept)) { dialog, _ ->
                viewModel.withdrawMureng()
            }
            .setNegativeButton(resources.getString(R.string.setting_withdrawal_dialog_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun subscribeUi() {
        viewModel.navigateToPushAlert.observe(this) {
            it?.let {
                navigateToPushAlert(it)
            }
        }

        viewModel.navigateToLogin.observe(this) {
            naivgateToLogin()
        }
    }

    private fun navigateToPushAlert(pushAlertSetting: PushAlertSetting) {
        Intent(this, PushAlertActivity::class.java)
            .putExtra(IntentKey.PUSH_ALERT_SETTING, pushAlertSetting)
            .also { startActivity(it) }
    }

    private fun naivgateToLogin() {
        Intent(this, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .also { startActivity(it) }
    }
}
