package com.engdiary.mureng.ui.signup.complete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySignupCompleteBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupCompleteActivity: BaseActivity<ActivitySignupCompleteBinding> (R.layout.activity_signup_complete) {
    override val viewModel: SingupCompleteViewModel by viewModels<SingupCompleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.next.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}