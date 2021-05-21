package com.engdiary.mureng.ui.social_best

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.util.HorizontalItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestTabFragment : BaseFragment<BestTabFragmentBinding>(R.layout.best_tab_fragment) {

    override val viewModel: BestTabViewModel by viewModels<BestTabViewModel>()
    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter(AnswerRecyclerType.TYPE_BEST,viewModel) }
    private val questionAdapter: QuestionAdapter by lazy { QuestionAdapter(viewModel) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvSocialPopQues.apply {
            adapter = questionAdapter
        }

        binding.rvSocialPopAns.apply {
            adapter = answerAdapter
            this.addItemDecoration(HorizontalItemDecorator(12))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

}