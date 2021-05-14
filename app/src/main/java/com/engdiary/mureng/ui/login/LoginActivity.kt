package com.engdiary.mureng.ui.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityLoginBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.main.MainViewModel


class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val TAG = "LoginActivity"

    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding.googleLogin.setOnClickListener {
        }

        binding.kakaoLogin.setOnClickListener {
        }


        setContentView(binding.root)

//        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
//
//
//        binding.kakaoLogin.setOnClickListener {
//            Log.i(TAG, "Log i")
//        }

    }
}