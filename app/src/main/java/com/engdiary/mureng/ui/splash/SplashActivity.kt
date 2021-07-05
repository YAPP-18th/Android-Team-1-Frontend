package com.engdiary.mureng.ui.splash


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySplashBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.login.LoginActivity
import com.engdiary.mureng.ui.main.MainActivity
import com.engdiary.mureng.util.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    val SPLASH_VIEW_TIME: Long = 2000

    override val viewModel: SplashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.postRefreshAccessToken()
        }, SPLASH_VIEW_TIME)

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.navigateToLogin.observe(this){
            startActivity(LoginActivity::class, isFinish = true)
        }
        viewModel.navigateToMain.observe(this){
            startActivity(MainActivity::class, isFinish = true)
        }
    }
}
