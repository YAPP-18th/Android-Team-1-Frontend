package com.engdiary.mureng.ui.social_best

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.databinding.BestTabFragmentBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.util.HorizontalItemDecorator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BestTabFragment : BaseFragment<BestTabFragmentBinding>(R.layout.best_tab_fragment) {

    override val viewModel: BestTabViewModel by viewModels<BestTabViewModel>()
    val handler : Handler = Handler()
    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter(AnswerRecyclerType.TYPE_BEST ,viewModel, handler) }
    private val questionAdapter: QuestionAdapter by lazy { QuestionAdapter(QuestionRecyclerType.TYPE_QUES_POP,viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvSocialPopQues.apply {
            adapter = questionAdapter
        }

        binding.rvSocialPopAns.apply {
            adapter = answerAdapter
            this.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> {
                            binding.rvSocialPopAns.parent?.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
            this.addItemDecoration(HorizontalItemDecorator(12))
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }


}