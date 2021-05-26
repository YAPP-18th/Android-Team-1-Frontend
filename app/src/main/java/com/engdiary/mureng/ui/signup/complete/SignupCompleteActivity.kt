package com.engdiary.mureng.ui.signup.complete

import android.os.Bundle
import androidx.activity.viewModels
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySignupCompleteBinding
import com.engdiary.mureng.ui.base.BaseActivity

class SignupCompleteActivity: BaseActivity<ActivitySignupCompleteBinding> (R.layout.activity_signup_complete) {
    override val viewModel: SingupCompleteViewModel by viewModels<SingupCompleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}