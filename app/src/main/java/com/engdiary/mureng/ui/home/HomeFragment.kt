package com.engdiary.mureng.ui.home

import android.graphics.Color
import android.net.wifi.hotspot2.pps.HomeSp
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.HomeFragmentBinding
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.write_diary.HintAdapter
import com.engdiary.mureng.ui.write_diary.HintDecoration
import com.engdiary.mureng.ui.write_diary.WriteDiaryContentActivity
import com.engdiary.mureng.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    val handler : Handler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.vm = viewModel

        binding.reply.setBackgroundColor(Color.BLACK)

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
        }

        viewModel.todayQuestion.observe(viewLifecycleOwner, Observer {
            binding.todayQuestion.text = it.content
        })

        viewModel.todayExpression.observe(viewLifecycleOwner, Observer { expressions ->
            expressions?.let { initHintAdapter(binding.homeTodayExpression, TodayExpressionAdapter(it, viewModel)) }
        })


        viewModel.checkReplied.observe(viewLifecycleOwner, Observer {
            if(it.replied == true) {
                binding.reply.text = "2시간 후에 답변을 할 수 있어요"
            } else {
                binding.reply.text = "답변하기"
            }
        })

    }

    private fun initHintAdapter(
        hints: RecyclerView,
        hintAdapter: TodayExpressionAdapter
    ) {
        hints.adapter = hintAdapter
        hints.addItemDecoration(HintDecoration(HomeFragment.HINT_SPAN_COUNT, HomeFragment.HINT_SPACING.dpToPx()))
        hintAdapter.notifyDataSetChanged()
    }


    companion object {
        private const val HINT_SPAN_COUNT = 2
        private const val HINT_SPACING = 20
    }
}

