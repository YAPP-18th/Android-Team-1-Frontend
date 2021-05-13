package com.engdiary.mureng.ui.social_myques

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.MyQuesFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.social_best.BestTabFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyQuesFragment : BaseFragment<MyQuesFragmentBinding>(R.layout.my_ques_fragment) {

    override val viewModel: MyQuesViewModel by viewModels<MyQuesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}