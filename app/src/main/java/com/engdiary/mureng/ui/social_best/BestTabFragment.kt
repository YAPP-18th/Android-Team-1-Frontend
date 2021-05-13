package com.engdiary.mureng.ui.social_best

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestTabFragment : BaseFragment<BestTabFragmentBinding>(R.layout.best_tab_fragment) {

    override val viewModel: BestTabViewModel by viewModels<BestTabViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}