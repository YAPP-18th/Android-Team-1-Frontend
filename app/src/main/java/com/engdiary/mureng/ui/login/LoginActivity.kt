package com.engdiary.mureng.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityLoginBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    val duration = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

//        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.googleLogin.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "준비중 입니다.", duration)
            toast.show()
        }

        binding.kakaoLogin.setOnClickListener {

        }

    }
}