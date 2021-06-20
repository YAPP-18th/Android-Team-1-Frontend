package com.engdiary.mureng.ui.social_qcreate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityMainBinding
import com.engdiary.mureng.databinding.ActivitySocialQcreateBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.base.navigate
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.main.MainViewModel
import com.engdiary.mureng.ui.my.MyPageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialQcreateActivity : BaseActivity<ActivitySocialQcreateBinding>(R.layout.activity_social_qcreate) {

    override val viewModel: SocialQcreateViewModel by viewModels<SocialQcreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.backButton.observe(this, Observer {
            if(it) finish()
        })

        viewModel.registerQues.observe(this, Observer {
            if(it) finish()
        })

    }
}