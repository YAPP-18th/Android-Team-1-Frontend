package com.engdiary.mureng.ui.my

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.FragmentScrapBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.home.ScrapListType
import com.engdiary.mureng.ui.home.TodayExpressionAdapter
import com.engdiary.mureng.ui.social_best.AnswerAdapter
import com.engdiary.mureng.ui.social_best.AnswerRecyclerType
import com.engdiary.mureng.ui.write_diary.HintDecoration
import com.engdiary.mureng.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScrapFragment : BaseFragment<FragmentScrapBinding>(R.layout.fragment_scrap) {
    override val viewModel: MyScrapViewModel by viewModels<MyScrapViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)



        viewModel.todayExpression.observe(viewLifecycleOwner, Observer { expressions ->
            expressions?.let {
                binding.homeTodayExpression.adapter = TodayExpressionAdapter(ScrapListType.TYPE_SCRAP, it, viewModel)
            }
        })

    }
}

