package com.engdiary.mureng.ui.social_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.databinding.ActivitySocialDetailBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.base.navigate
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.main.MainViewModel
import com.engdiary.mureng.ui.my.MyPageFragment
import com.engdiary.mureng.ui.social.SocialFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class SocialDetailActivity : BaseActivity<ActivitySocialDetailBinding>(R.layout.activity_social_detail) {

    override val viewModel: SocialDetailViewModel by viewModels<SocialDetailViewModel>()


    private val questionData: QuestionData?
        get() = intent.getSerializableExtra("questionData") as? QuestionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        questionData?.let { viewModel.getQuesData(it) } ?: finish()

        viewModel.backButton.observe(this, Observer {
            if(it) {
                // TODO Back Button 기능 구현
            }
        })



    }
}