package com.engdiary.mureng.ui.home

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.HomeFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}