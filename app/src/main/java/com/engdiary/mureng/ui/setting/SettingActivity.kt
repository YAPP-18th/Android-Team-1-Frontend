package com.engdiary.mureng.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.databinding.ActivitySettingBinding
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.push_alert.PushAlertActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imagebuttonSettingBack.setOnClickListener { finish() }
        binding.textviewSettingMaker.setOnClickListener {
            val intent = Intent(this, DevelopPersonActivity::class.java)
            startActivity(intent)
        }
        initPushAlert(binding.textviewSettingPushalert)
        initLogout(binding.textviewSettingLogout)
        initWithdrawal(binding.textviewSettingWithdraw)
    }

    private fun initPushAlert(textviewSettingPushalert: TextView) {
        textviewSettingPushalert.setOnClickListener { navigateToPushAlert() }
    }

    private fun navigateToPushAlert() {
        Intent(this, PushAlertActivity::class.java)
            .also { startActivity(it) }
    }

    private fun initLogout(textviewSettingLogout: TextView) {
        textviewSettingLogout.setOnClickListener { showLogoutDialog(this) }
    }

    private fun initWithdrawal(textviewSettingWithdraw: TextView) {
        textviewSettingWithdraw.setOnClickListener { showWithdrawalDialog(this) }
    }

    private fun showLogoutDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.setting_logout_dialog_title))
            .setPositiveButton(resources.getString(R.string.setting_logout_dialog_accept)) { dialog, _ ->
                // todo 로그아웃 구현
            }
            .setNegativeButton(resources.getString(R.string.setting_logout_dialog_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showWithdrawalDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.setting_withdrawal_dialog_title))
            .setMessage(resources.getString(R.string.setting_withdrawal_dialog_message))
            .setPositiveButton(resources.getString(R.string.setting_withdrawal_dialog_accept)) { dialog, _ ->
                // todo 로그아웃 구현
            }
            .setNegativeButton(resources.getString(R.string.setting_withdrawal_dialog_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
