package com.engdiary.mureng.ui.home

import android.content.Intent
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
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.ui.setting.WebviewActivity
import com.engdiary.mureng.ui.signup.nickname.SignupNickNameActivity
import com.engdiary.mureng.ui.write_diary.HintAdapter
import com.engdiary.mureng.ui.write_diary.HintDecoration
import com.engdiary.mureng.ui.write_diary.WriteDiaryContentActivity
import com.engdiary.mureng.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.todayExpression.observe(viewLifecycleOwner, Observer { expressions ->
            expressions?.let {
               binding.homeTodayExpression.adapter = TodayExpressionAdapter(ScrapListType.TYPE_HOME, it, viewModel)
            }
        })

        viewModel.question.observe(this) { test ->
        }

//        viewModel.question.observe()

        binding.btnReply.setOnClickListener {
//            Intent(this, WebviewActivity::class.java)
//                    .putExtra("mode", IntentKey.TERMS)
//                    .also { startActivity(it) }
            Intent(context, WriteDiaryContentActivity::class.java)
                    .putExtra(IntentKey.QUESTION, viewModel.question.value)
                    .also { startActivity(it) }
        }

    }
}

