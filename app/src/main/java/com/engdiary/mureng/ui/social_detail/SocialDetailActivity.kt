package com.engdiary.mureng.ui.social_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySocialDetailBinding
import com.engdiary.mureng.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialDetailActivity : BaseActivity<ActivitySocialDetailBinding>(R.layout.activity_social_detail) {

    override val viewModel: SocialDetailViewModel by viewModels<SocialDetailViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.backButton.observe(this, Observer {
            if(it) finish()
        })

    }
}