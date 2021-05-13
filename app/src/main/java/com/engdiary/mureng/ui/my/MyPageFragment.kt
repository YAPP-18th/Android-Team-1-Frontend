package com.engdiary.mureng.ui.my

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.HomeFragmentBinding
import com.engdiary.mureng.databinding.MyPageFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<MyPageFragmentBinding>(R.layout.my_page_fragment) {

    override val viewModel: MyPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}