package com.engdiary.mureng.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.databinding.ActivityLoginBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.main.MainActivity
import com.engdiary.mureng.ui.main.MainViewModel
import com.engdiary.mureng.ui.signup.agreement.SignupTermsActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    val duration = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.googleLogin.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "준비중 입니다.", duration)
            toast.show()
        }

        binding.kakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
            }
        }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.navigateToSignup.observe(this) {
            startActivity(Intent(this, SignupTermsActivity::class.java))
        }

        viewModel.navigateToHome.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}