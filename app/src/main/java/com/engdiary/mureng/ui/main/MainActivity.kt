package com.engdiary.mureng.view.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.base.navigate
import com.engdiary.mureng.ui.home.HomeFragment
import com.engdiary.mureng.ui.main.MainViewModel
import com.engdiary.mureng.ui.my.MyPageFragment
import com.engdiary.mureng.ui.social.SocialFragment
import com.engdiary.mureng.ui.writing.WritingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.Nullable
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<com.engdiary.mureng.databinding.ActivityMainBinding>(R.layout.activity_main) {

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


        //TODO Writing Activity로 연결해야함
        viewModel.selectWriting.observe(this, Observer {
            if(it) {
                WritingFragment().navigate(supportFragmentManager, fl_main.id)
            }
        })


    }
}