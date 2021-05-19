package com.engdiary.mureng.ui.push_alert

import android.os.Bundle
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityPushAlertBinding
import com.engdiary.mureng.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PushAlertActivity() : BaseActivity<ActivityPushAlertBinding>(R.layout.activity_push_alert) {
    override val viewModel: PushAlertViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.imagebuttonPushalertBack.setOnClickListener { finish() }
    }
}
