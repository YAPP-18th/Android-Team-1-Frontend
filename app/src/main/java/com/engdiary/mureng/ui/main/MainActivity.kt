package com.engdiary.mureng.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityMainBinding
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.di.MurengApplication
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.base.navigate
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.my.MyPageFragment
import com.engdiary.mureng.ui.setting.SettingActivity
import com.engdiary.mureng.ui.social.SocialFragment
import com.engdiary.mureng.ui.write_diary.WriteDiaryContentActivity
import com.engdiary.mureng.util.startActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        // TODO 서버 통신을 위한 임의의 test_jwt 추가 (나중에 지워야함)
        authManager.test_jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpZGVudGl0eSIsIm5pY2tuYW1lIjoi7YWM7Iqk7Yq47Jyg7KCAIiwiaWF0IjozNDk2NzA1MTEwLCJleHAiOjM1MDE4ODkxMTB9.dkvgL0xVaUBGwbN3STJNT3lID5ku5a6qkfkFUg_GZ8WFi5QTiOuduLYgD85yZlzRnjmoKxjjwii4Mr11bZsOHA"

        viewModel.selectHome.observe(this, Observer {
            if(it) {
                HomeFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })

        viewModel.selectMyPage.observe(this, Observer {
            if(it) {
//                MyPageFragment().navigate(supportFragmentManager, fl_main.id)
                startActivity(SettingActivity::class, isFinish = false)
            }
        })

        viewModel.selectSocial.observe(this, Observer {
            if(it) {
                SocialFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })


        viewModel.selectWriting.observe(this, Observer {
            if(it) {
                startActivity(WriteDiaryContentActivity::class,isFinish = false)
            }
        })


    }
}