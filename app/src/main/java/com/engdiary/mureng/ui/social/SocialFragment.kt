package com.engdiary.mureng.ui.social

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.SocialFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.social.SocialViewModel.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SocialFragment : BaseFragment<SocialFragmentBinding>(R.layout.social_fragment) {

    override val viewModel: SocialViewModel by viewModels<SocialViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            fragmentManager = childFragmentManager
            vm = viewModel
            lifecycleOwner = this@SocialFragment.viewLifecycleOwner
        }

    }
}