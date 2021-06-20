package com.engdiary.mureng.ui.signup.agreement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivitySignupTermsBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.signup.nickname.SignupNickNameActivity
import com.engdiary.mureng.ui.signup.terms.SignupTermsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupTermsActivity: BaseActivity<ActivitySignupTermsBinding>(R.layout.activity_signup_terms) {

    override val viewModel: SignupTermsViewModel by viewModels<SignupTermsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.next.setOnClickListener {

            if(viewModel.selectAllTerms.value!! && viewModel.selectInfoTerms.value!!) {
                val intent = Intent(this, SignupNickNameActivity::class.java)
                startActivity(intent)
            } else {
                val termsGuideToast = Toast.makeText(this, "약관을 동의해 주세요.", Toast.LENGTH_SHORT)
                termsGuideToast.show()
            }

        }

        viewModel.selectAllTerms.observe(this, Observer {

            if(it) {
                binding.checkboxAll.isChecked = it
                binding.checkboxCheese.isChecked = it
                binding.checkboxCheese2.isChecked = it
            }

        })

        viewModel.selectServiceTerms.observe(this, Observer {

            if(!it) {
                binding.checkboxAll.isChecked = false
            }

        })

        viewModel.selectInfoTerms.observe(this, Observer {

            if(!it) {
                binding.checkboxAll.isChecked = false
            }

        })

    }

}