package com.engdiary.mureng.ui.push_alert

import android.os.Bundle
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.PushAlertSetting
import com.engdiary.mureng.databinding.ActivityPushAlertBinding
import com.engdiary.mureng.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PushAlertActivity() : BaseActivity<ActivityPushAlertBinding>(R.layout.activity_push_alert) {
    override val viewModel: PushAlertViewModel by viewModels()

    private val pushAlertSetting: PushAlertSetting?
        get() = intent.getParcelableExtra(IntentKey.PUSH_ALERT_SETTING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.viewModel = viewModel

        pushAlertSetting?.let { viewModel.setPushAlertSetting(it) }

        binding.imagebuttonPushalertBack.setOnClickListener { finish() }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.isDailyPushAlertActive.observe(this) { isActive ->
            isActive?.let { viewModel.postDailyPushAlertActive(it) }
        }

        viewModel.isLikePushAlertActive.observe(this) { isActive ->
            isActive?.let { viewModel.postLikePushAlertActive(it) }
        }
    }
}
