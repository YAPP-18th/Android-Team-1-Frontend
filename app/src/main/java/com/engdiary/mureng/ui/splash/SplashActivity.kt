package com.engdiary.mureng.ui.splash

import android.content.Intent
import com.engdiary.mureng.BR


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySplashBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.login.LoginActivity
import com.engdiary.mureng.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    val SPLASH_VIEW_TIME: Long = 2000

    override val viewModel: SplashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        Handler().postDelayed({ //delay를 위한 handler
            if(authManager.accessToken.isEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish()
        }, SPLASH_VIEW_TIME)

    }
}