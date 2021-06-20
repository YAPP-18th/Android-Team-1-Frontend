package com.engdiary.mureng.ui.signup.nickname

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySignupNicknameBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.signup.agreement.SignupTermsActivity
import com.engdiary.mureng.ui.signup.complete.SignupCompleteActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupNickNameActivity: BaseActivity<ActivitySignupNicknameBinding>(R.layout.activity_signup_nickname) {

    override val viewModel: SignupNickNameViewModel by viewModels<SignupNickNameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.navigateToSignupComplete.observe(this) {
            startActivity(Intent(this, SignupCompleteActivity::class.java))
        }

        viewModel.next.observe(this, Observer {
        })

        viewModel.isDuplicate.observe(this, Observer {
            Log.i("DUPLICATE", it.toString())
        })

    }
}