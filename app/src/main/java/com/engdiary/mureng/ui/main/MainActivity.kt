package com.engdiary.mureng.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityMainBinding
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.base.navigate
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.my.MyPageFragment
import com.engdiary.mureng.ui.social.SocialFragment
import com.engdiary.mureng.ui.write_diary.WritingDiaryContentActivity
import com.engdiary.mureng.util.startActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.selectHome.observe(this, Observer {
            if(it) {
                HomeFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })

        viewModel.selectMyPage.observe(this, Observer {
            if(it) {
                MyPageFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })

        viewModel.selectSocial.observe(this, Observer {
            if(it) {
                SocialFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })


        viewModel.selectWriting.observe(this, Observer {
            if(it) {
                startActivity(WritingDiaryContentActivity::class,isFinish = false)
            }
        })


    }
}