package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.databinding.FragmentAwardBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.social_best.AnswerAdapter
import com.engdiary.mureng.ui.social_best.AnswerRecyclerType
import com.engdiary.mureng.ui.social_best.BestTabViewModel
import com.engdiary.mureng.ui.social_best.QuestionAdapter
import com.engdiary.mureng.util.HorizontalItemDecorator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AwardFragment : BaseFragment<FragmentAwardBinding>(R.layout.fragment_award) {

    override val viewModel: BestTabViewModel by viewModels<BestTabViewModel>()
    val handler : Handler = Handler()
    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter(AnswerRecyclerType.TYPE_BEST ,viewModel, handler) }
    private val questionAdapter: QuestionAdapter by lazy { QuestionAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

    }


    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}


