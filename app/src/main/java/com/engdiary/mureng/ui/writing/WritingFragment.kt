package com.engdiary.mureng.ui.writing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.SocialFragmentBinding
import com.engdiary.mureng.databinding.WritingFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.social.SocialViewModel

class WritingFragment : BaseFragment<WritingFragmentBinding>(R.layout.writing_fragment) {

    override val viewModel: WritingViewModel by viewModels<WritingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}