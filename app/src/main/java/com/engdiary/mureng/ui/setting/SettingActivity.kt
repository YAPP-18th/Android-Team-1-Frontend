package com.engdiary.mureng.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
